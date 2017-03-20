package com.daoautogen;

/**
 * Created by uziel on 17/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        String tableName = "U_USUARIO";
        String classBaseName = "Usuario";
        String classPackage = "com.test.dao.usuario";
        DaoMetadataTable metadata = new DaoMetadataTable(tableName, classBaseName, classPackage);

        DaoColumn col = new DaoColumn(true, JDBCType.INTEGER,"USR_ID",0, JavaType.INTEGER, "id",1);
        DaoColumn col2 = new DaoColumn(false, JDBCType.VARCHAR,"USR_USERNAME",150, JavaType.STRING, "username",2);

        metadata.addColumn(col);
        metadata.addColumn(col2);

        CodeDaoGenerator gen = new CodeDaoGenerator();
        gen.generateDao(metadata);
    }
}
