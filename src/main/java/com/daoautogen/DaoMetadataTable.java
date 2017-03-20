package com.daoautogen;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by uziel on 17/03/2017.
 */
public class DaoMetadataTable {

    private String tableName;
    private String classBaseName;
    private String classPackage;

    private Map<Integer, DaoColumn> map = new HashMap<>();
    private int count = 0;

    public DaoMetadataTable(String tableName, String classBaseName, String classPackage) {
        this.tableName = tableName;
        this.classBaseName = classBaseName;
        this.classPackage = classPackage;
    }

    public String getTableName() {
        return tableName;
    }

    public String getClassBaseName() {
        return classBaseName;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public Map<Integer, DaoColumn> getMap() {
        return map;
    }

    public void addColumn(DaoColumn col) {
        map.put(++count, col);
    }

}
