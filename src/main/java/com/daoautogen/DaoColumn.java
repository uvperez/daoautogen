package com.daoautogen;


import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by uziel on 17/03/2017.
 */
public class DaoColumn implements ColumnJMap {

    private boolean primaryKey;
    private int length;

    private SimpleColJMap colMap;

    public DaoColumn(boolean primaryKey, JDBCType jdbcType, String colName,
                     int length, JavaType javaType, String javaPropertyName, int index, boolean notNull) {
        this.primaryKey = primaryKey;
        this.length = length;
        this.colMap = new SimpleColJMap(jdbcType, javaType.getJavaTypeClass(), colName, javaType, javaPropertyName, index, notNull);
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    @Override
    public JDBCType getSqlType() {
        return colMap.getSqlType();
    }

    @Override
    public String getColName() {
        return colMap.getColName();
    }

    public int getLength() {
        return length;
    }

    @Override
    public JavaType getJavaType() {
        return colMap.getJavaType();
    }

    @Override
    public String getJavaPropertyName() {
        return colMap.getJavaPropertyName();
    }

    @Override
    public int getIndex() {
        return colMap.getIndex();
    }

    @Override
    public Class<?> getJavaTypeClass() {
        return colMap.getJavaTypeClass();
    }

    @Override
    public boolean isRequired() {
        return colMap.isRequired();
    }

    public String getPreparedStatementSetData(int index){
        switch (colMap.getJavaType()) {
            case STRING: return "ps.setString("+index+", dto.get" + Utils.getCamelCaseName(colMap.getJavaPropertyName()) + "());";
            case INTEGER: return "ps.setInt("+index+", dto.get" + Utils.getCamelCaseName(colMap.getJavaPropertyName()) + "());";
            default: return null;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DaoColumn{");
        sb.append("primaryKey=").append(primaryKey);
        sb.append(", length=").append(length);
        sb.append(", colMap=").append(colMap);
        sb.append('}');
        return sb.toString();
    }
}

