package com.hibernate.myexperiments.custom.dialect.function;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import java.util.List;

/**
 * Created by kaustubh on 7/21/16.
 */
public class DB2SchemaSQLFunction implements SQLFunction {


    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        return "(select min(i.in_market_date) form xd.interaction i where i.storyboard_id = id)";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return false;
    }

    @Override
    public Type getReturnType(Type firstArgumentType, Mapping mapping) throws QueryException {
        return new StringType();
    }
}
