package com.daoautogen;


import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by uziel on 17/03/2017.
 */
public class DaoColumn implements ColumnJMap {

    private boolean primaryKey;
    private JDBCType sqlType;
    private String colName;
    private int length;

    private JavaType javaType;
    private String javaPropertyName;
    private int index;

    public DaoColumn(boolean primaryKey, JDBCType sqlType, String colName,
                     int length, JavaType javaType, String javaPropertyName, int index) {
        this.primaryKey = primaryKey;
        this.sqlType = sqlType;
        this.colName = colName;
        this.length = length;
        this.javaType = javaType;
        this.javaPropertyName = javaPropertyName;
        this.index = index;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    @Override
    public JDBCType getSqlType() {
        return sqlType;
    }

    @Override
    public String getColName() {
        return colName;
    }

    public int getLength() {
        return length;
    }

    @Override
    public JavaType getJavaType() {
        return javaType;
    }

    @Override
    public String getJavaPropertyName() {
        return javaPropertyName;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Class<?> getJavaTypeClass() {
        switch (javaType) {
            case INTEGER:
                return Integer.class;
            case STRING:
                return String.class;
            case BIGDECIMAL:
                return BigDecimal.class;
            case LONG:
                return Long.class;
            case DATE:
                return Date.class;
            default:
                throw new IllegalStateException("java type no soportado");
        }
    }

    public String getPreparedStatementSetData(int index){
        switch (javaType) {
            case STRING: return "ps.setString("+index+", dto.get" + Utils.getCamelCaseName(javaPropertyName) + "());";
            case INTEGER: return "ps.setInt("+index+", dto.get" + Utils.getCamelCaseName(javaPropertyName) + "());";
            default: return null;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DaoColumn{");
        sb.append("primaryKey=").append(primaryKey);
        sb.append(", sqlType=").append(sqlType);
        sb.append(", colName='").append(colName).append('\'');
        sb.append(", length=").append(length);
        sb.append(", javaType=").append(javaType);
        sb.append(", javaPropertyName='").append(javaPropertyName).append('\'');
        sb.append(", index=").append(index);
        sb.append('}');
        return sb.toString();
    }
}

