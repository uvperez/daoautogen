package com.daoautogen;

/**
 * Created by uziel on 19/03/2017.
 */
public interface ColumnJMap {
    JDBCType getSqlType();

    String getColName();

    JavaType getJavaType();

    String getJavaPropertyName();

    int getIndex();

    Class<?> getJavaTypeClass();
}
