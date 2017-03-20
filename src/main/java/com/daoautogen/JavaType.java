package com.daoautogen;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by uziel on 19/03/2017.
 */
public enum JavaType {
    LONG(Long.class),
    INTEGER(Integer.class),
    BIGDECIMAL(BigDecimal.class),
    STRING(String.class),
    DATE(Date.class);

    private Class<?> javaTypeClass;

    JavaType(Class<?> javaTypeClass) {
        this.javaTypeClass = javaTypeClass;
    }

    public Class<?> getJavaTypeClass() {
        return javaTypeClass;
    }

}
