package com.daoautogen;

/**
 * Created by uziel on 17/03/2017.
 */
public class Utils {

    public static String getCamelCaseName(String javaPropertyName) {
        return javaPropertyName.substring(0, 1).toUpperCase() +
                javaPropertyName.substring(1, javaPropertyName.length());
    }
}
