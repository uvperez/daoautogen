package com.daoautogen;

/**
 * Created by uziel on 19/03/2017.
 */
public class SimpleColJMap implements ColumnJMap {

    private DaoColumn.JDBCType jdbcType;
    private Class<?> javaTypeClass;
    private String colName;
    private DaoColumn.DaoColumnJavaType javaType;
    private String javaPropertyName;
    private int index;

    public SimpleColJMap(DaoColumn.JDBCType jdbcType, Class<?> javaTypeClass, String colName,
                         DaoColumn.DaoColumnJavaType javaType, String javaPropertyName, int index) {
        this.jdbcType = jdbcType;
        this.javaTypeClass = javaTypeClass;
        this.colName = colName;
        this.javaType = javaType;
        this.javaPropertyName = javaPropertyName;
        this.index = index;
    }

    @Override
    public DaoColumn.JDBCType getSqlType() {
        return jdbcType;
    }

    @Override
    public String getColName() {
        return colName;
    }

    @Override
    public DaoColumn.DaoColumnJavaType getJavaType() {
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
        return javaTypeClass;
    }
}
