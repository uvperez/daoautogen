package com.daoautogen;

import com.sun.codemodel.JBlock;

/**
 * Created by uziel on 19/03/2017.
 */
public interface JdbcTransform {

    public void makeSetsDirectStatements(JBlock block, String stmtVarName, ColumnJMap col, int index);

}
