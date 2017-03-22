package com.daoautogen;

/**
 * Created by uziel on 17/03/2017.
 */
public class Main {
    public static void main(String[] args) {
        String tableName = "U_USER";
        String classBaseName = "User";
        String classPackage = "com.test.dao.user";
        DaoMetadataTable metadata = new DaoMetadataTable(tableName, classBaseName, classPackage);

//        boolean primaryKey, JDBCType jdbcType, String colName,
//        int length, JavaType javaType, String javaPropertyName, int index, boolean notNull

        DaoColumn col = new DaoColumn(true, JDBCType.INTEGER,"USR_ID",0, JavaType.INTEGER, "id",1, true);
        DaoColumn col2 = new DaoColumn(false, JDBCType.VARCHAR,"USR_USERNAME",150, JavaType.STRING, "username",2, true);
        DaoColumn col3 = new DaoColumn(false, JDBCType.VARCHAR,"USR_ALIAS",100, JavaType.STRING, "alias",3, false);
        DaoColumn col4 = new DaoColumn(false, JDBCType.TIMESTAMP,"USR_CREATED",0, JavaType.DATE, "created",4, true);

        metadata.addColumn(col);
        metadata.addColumn(col2);
        metadata.addColumn(col3);
        metadata.addColumn(col4);

        CodeDaoGenerator gen = new CodeDaoGenerator();
        gen.generateDao(metadata);
    }
}
