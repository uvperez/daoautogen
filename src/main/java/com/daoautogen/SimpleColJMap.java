package com.daoautogen;

/**
 * Created by uziel on 19/03/2017.
 */
public class SimpleColJMap implements ColumnJMap {

    private JDBCType jdbcType;
    private Class<?> javaTypeClass;
    private String colName;
    private JavaType javaType;
    private String javaPropertyName;
    private int index;
    private boolean required;

    public SimpleColJMap(JDBCType jdbcType, Class<?> javaTypeClass, String colName,
                         JavaType javaType, String javaPropertyName, int index, boolean required) {
        this.jdbcType = jdbcType;
        this.javaTypeClass = javaTypeClass;
        this.colName = colName;
        this.javaType = javaType;
        this.javaPropertyName = javaPropertyName;
        this.index = index;
        this.required = required;
    }

    @Override
    public JDBCType getSqlType() {
        return jdbcType;
    }

    @Override
    public String getColName() {
        return colName;
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
        return javaTypeClass;
    }

    @Override
    public boolean isRequired() {
        return required;
    }


}
