package com.hibernate.myexperiments.custom.dialect;

import com.hibernate.myexperiments.custom.dialect.function.DB2SchemaSQLFunction;
import org.hibernate.dialect.DB2Dialect;

/**
 * Created by kaustubh on 7/21/16.
 */
public class CustomDB2Dialect extends DB2Dialect {

    public CustomDB2Dialect() {
        super();
        registerFunction("default_schema", new DB2SchemaSQLFunction());
    }
}
