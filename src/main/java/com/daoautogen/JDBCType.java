package com.daoautogen;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by uziel on 19/03/2017.
 */
public enum JDBCType {
    INTEGER(Integer.class),
    NUMBER(BigDecimal.class),
    VARCHAR(String.class),
    TIMESTAMP(Timestamp.class),
    CLOB(String.class),
    DATE(Date.class);

    private Class<?> jdbcClass;

    JDBCType(Class<?> jdbcClass) {
        this.jdbcClass = jdbcClass;
    }

    public Class<?> getJdbcClass() {
        return jdbcClass;
    }

}
