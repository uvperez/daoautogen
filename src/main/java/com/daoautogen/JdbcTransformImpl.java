package com.daoautogen;

import com.sun.codemodel.JBlock;

import java.sql.Types;

/**
 * Created by uziel on 20/03/2017.
 */
public class JdbcTransformImpl implements JdbcTransform {
    @Override
    public void makeSetsDirectStatements(JBlock block, String stmtVarName, ColumnJMap col) {
        Class<?> javaTypeClass = col.getJavaTypeClass();
        Class<?> jdbcClass = col.getSqlType().getJdbcClass();
        boolean required = col.isRequired();
        if (javaTypeClass.equals(Integer.class)) {
            if (jdbcClass.equals(Integer.class) && required) {
                block.directStatement(setString(stmtVarName, "Int", col.getIndex(), col.getJavaPropertyName()));
            }
            if (jdbcClass.equals(Integer.class) && !required) {
                block.directStatement("if ( " + "dto.get" + Utils.getCamelCaseName(col.getJavaPropertyName()) + "() == null ) {" );
                block.directStatement( stmtVarName + ".setNull(" + col.getIndex() + ", java.sql.Types.NULL); ");
                block.directStatement("} else {");
                block.directStatement(setString(stmtVarName, "Int", col.getIndex(), col.getJavaPropertyName()));
                block.directStatement("}");
            }
        }
    }

    private static String setString(String stmtVarName, String postSet, int colIndex, String javaPropertyName) {
        return stmtVarName + ".set" + postSet + "(" + colIndex + ", dto.get" + Utils.getCamelCaseName(javaPropertyName) + "());";
    }
}
