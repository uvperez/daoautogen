package com.daoautogen;

import com.sun.codemodel.JBlock;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * Created by uziel on 20/03/2017.
 */
public class JdbcTransformImpl implements JdbcTransform {

    @Override
    public void makeSetsDirectStatements(JBlock block, String stmtVarName, ColumnJMap col, int index) {
        setStatement(block, stmtVarName, col, index);
    }

    private void setStatement(JBlock block, String stmtVarName, ColumnJMap col, int index) {
        Class<?> javaTypeClass = col.getJavaTypeClass();
        Class<?> jdbcClass = col.getSqlType().getJdbcClass();
        boolean required = col.isRequired();

        // common cases
        if (javaTypeClass.equals(Integer.class) && jdbcClass.equals(Integer.class)) {
            setBlocks(Integer.class, "Int", col, required, jdbcClass, stmtVarName, block, index);
        }
        if (javaTypeClass.equals(BigDecimal.class) && jdbcClass.equals(BigDecimal.class)) {
            setBlocks(BigDecimal.class, "BigDecimal", col, required, jdbcClass, stmtVarName, block, index);
        }
        if (javaTypeClass.equals(String.class) && jdbcClass.equals(String.class)) {
            setBlocks(String.class, "String", col, required, jdbcClass, stmtVarName, block, index);
        }
        if (javaTypeClass.equals(Timestamp.class) && jdbcClass.equals(Timestamp.class)) {
            setBlocks(Timestamp.class, "Timestamp", col, required, jdbcClass, stmtVarName, block, index);
        }
        if (javaTypeClass.equals(Date.class) && jdbcClass.equals(Date.class)) {
            setBlocks(Date.class, "Date", col, required, jdbcClass, stmtVarName, block, index);
        }
        // special cases
        if (javaTypeClass.equals(java.util.Date.class) && jdbcClass.equals(Date.class)) {
            setBlocks(Date.class, col, required, jdbcClass, stmtVarName, block,
                    String.format("%s.setDate(%s, new java.sql.Date(dto.get%s().getTime()));",
                            stmtVarName, index, Utils.getCamelCaseName(col.getJavaPropertyName()))
                    , index);
        }
        if (javaTypeClass.equals(java.util.Date.class) && jdbcClass.equals(Timestamp.class)) {
            setBlocks(Timestamp.class, col, required, jdbcClass, stmtVarName, block,
                    String.format("%s.setTimestamp(%s, new java.sql.Timestamp(dto.get%s().getTime()));",
                            stmtVarName, index, Utils.getCamelCaseName(col.getJavaPropertyName()))
                    , index);
        }
        if (javaTypeClass.equals(Integer.class) && jdbcClass.equals(BigDecimal.class)) {
            setBlocks(BigDecimal.class, col, required, jdbcClass, stmtVarName, block,
                    String.format("%s.setBigDecimal(%s, java.math.BigDecimal.valueOf(dto.get%s()));",
                            stmtVarName, index, Utils.getCamelCaseName(col.getJavaPropertyName()))
                    , index);
        }
    }

    private static String setString(String stmtVarName, String postSet, int colIndex, String javaPropertyName) {
        return stmtVarName + ".set" + postSet + "(" + colIndex + ", dto.get" + Utils.getCamelCaseName(javaPropertyName) + "());";
    }

    private static void setBlocks(Class<?> clazz, String postSet, ColumnJMap col, boolean required,
                                  Class<?> jdbcClass, String stmtVarName, JBlock block, int index) {
        if (jdbcClass.equals(clazz) && required) {
            block.directStatement(setString(stmtVarName, postSet, index, col.getJavaPropertyName()));
        }
        if (jdbcClass.equals(clazz) && !required) {
            block.directStatement("if ( " + "dto.get" + Utils.getCamelCaseName(col.getJavaPropertyName()) + "() == null ) {" );
            block.directStatement("     " + stmtVarName + ".setNull(" + index + ", java.sql.Types.NULL); ");
            block.directStatement("} else {");
            block.directStatement("     " + setString(stmtVarName, postSet, index, col.getJavaPropertyName()));
            block.directStatement("}");
        }
    }

    private static void setBlocks(Class<?> clazz, ColumnJMap col, boolean required, Class<?> jdbcClass,
                                  String stmtVarName, JBlock block, String notnullStatement, int index) {
        if (jdbcClass.equals(clazz) && required) {
            block.directStatement(notnullStatement);
        }
        if (jdbcClass.equals(clazz) && !required) {
            block.directStatement("if ( " + "dto.get" + Utils.getCamelCaseName(col.getJavaPropertyName()) + "() == null ) {" );
            block.directStatement("     " + stmtVarName + ".setNull(" + index + ", java.sql.Types.NULL); ");
            block.directStatement("} else {");
            block.directStatement("     " + notnullStatement);
            block.directStatement("}");
        }
    }
}
