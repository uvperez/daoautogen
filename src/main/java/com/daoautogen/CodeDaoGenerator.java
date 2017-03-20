package com.daoautogen;


import com.sun.codemodel.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by uziel on 17/03/2017.
 */
public class CodeDaoGenerator {

    private JCodeModel jCodeModel = new JCodeModel();

    public CodeDaoGenerator() {
    }

    public void generateDao(DaoMetadataTable metadata) {
        try {
            JPackage jp = jCodeModel._package(metadata.getClassPackage());
            JDefinedClass dtoClass = jp._class(metadata.getClassBaseName() + "DTO");
            JDefinedClass daoInterface = jp._interface(metadata.getClassBaseName() + "DAO");
            JDefinedClass daoImplClass = jp._class(metadata.getClassBaseName() + "DAOImpl");
            daoImplClass._implements(daoInterface);

            // Construye DTOs
            for (int i = 1; i <= metadata.getMap().size(); i++) {
                DaoColumn column = metadata.getMap().get(i);

                JFieldVar field = dtoClass.field(JMod.PRIVATE, column.getJavaTypeClass(), column.getJavaPropertyName());

                JMethod getMethod = dtoClass.method(JMod.PUBLIC, column.getJavaTypeClass(), "get" + getCamelCaseName(column.getJavaPropertyName()));
                JBlock jgetBody = getMethod.body();
                jgetBody._return(field);

                JMethod setMethod = dtoClass.method(JMod.PUBLIC, jCodeModel.VOID, "set" + getCamelCaseName(column.getJavaPropertyName()));
                setMethod.param(column.getJavaTypeClass(), column.getJavaPropertyName());
                JBlock jsetBody = setMethod.body();
                jsetBody.directStatement("this." + column.getJavaPropertyName() + " = " + column.getJavaPropertyName() + ";");

            }

            // modifica clase impl DAO
            modificaClaseImpl(daoImplClass, dtoClass, metadata);

            // modifica interfaz DAO

            jCodeModel.build(new File("src/test/java"));

        } catch (JClassAlreadyExistsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void modificaClaseImpl(JDefinedClass daoImplClass, JDefinedClass dtoClass, DaoMetadataTable metadata) {
        {
            JMethod insert = daoImplClass.method(JMod.PUBLIC, jCodeModel.VOID, "insert");
            insert.param(dtoClass, "dto");
            insert.param(Connection.class, "connection");
            JBlock body = insert.body();
            JClass refPs = jCodeModel.ref(PreparedStatement.class);
            body.decl(refPs, "ps", JExpr._null());
            JTryBlock jTryBlock = body._try();
            JBlock bodyJTry = jTryBlock.body();
            bodyJTry.directStatement("String sqlInsert = \"" + getInsertString(metadata) + "\";");
            bodyJTry.directStatement("ps = connection.prepareStatement(sqlInsert);");
            registerInputParametersPs(bodyJTry,metadata);
            bodyJTry.directStatement("ps.executeUpdate();");

            JClass refSqlExc = jCodeModel.ref(SQLException.class);
            JCatchBlock jCatchBlock = jTryBlock._catch(refSqlExc);
            jCatchBlock.param("ex");
            JBlock aFinally = jTryBlock._finally();
            aFinally.directStatement("try { ");
            aFinally.directStatement("  if (ps != null) {");
            aFinally.directStatement("      ps.close();");
            aFinally.directStatement("  }");
            aFinally.directStatement("} catch (SQLException e) {}");
        }

        {
            JMethod update = daoImplClass.method(JMod.PUBLIC, jCodeModel.VOID, "update");
            update.param(dtoClass, "dto");
            update.param(java.sql.Connection.class, "connection");
        }

    }

    private void registerInputParametersPs(JBlock bodyJTry, DaoMetadataTable metadata) {
        Map<Integer, DaoColumn> map = metadata.getMap();
        for (int i = 1; i <= map.size(); i++) {
            DaoColumn daoColumn = map.get(i);
            bodyJTry.directStatement(daoColumn.getPreparedStatementSetData(i));
        }
    }

    private String getInsertString(DaoMetadataTable metadata) {
        Map<Integer, DaoColumn> map = metadata.getMap();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(metadata.getTableName());
        sb.append(" (");
        for (int i = 1; i <= map.size(); i++) {
            DaoColumn daoColumn = map.get(i);
            sb.append(daoColumn.getColName());
            if (i < map.size()) {
                sb.append(",");
            }
        }

        sb.append(") VALUES(");

        for (int i = 1; i <= map.size(); i++) {
            sb.append("?");
            if (i < map.size()) {
                sb.append(",");
            }
        }

        sb.append(")");

        return sb.toString();
    }

    static String getCamelCaseName(String javaPropertyName) {
        return javaPropertyName.substring(0, 1).toUpperCase() +
                javaPropertyName.substring(1, javaPropertyName.length());
    }

}

