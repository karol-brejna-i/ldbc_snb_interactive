package com.ldbc.impls.workloads.ldbc.snb.tygrysek.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;

import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.SingletonOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tygrysek.TygrysekDbConnectionState;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.text.ParseException;

public abstract class TygrysekSingletonOperationHandler<TOperation extends Operation<TOperationResult>, TOperationResult>
        implements SingletonOperationHandler<TOperationResult, TOperation, TygrysekDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, TygrysekDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try (Session session = state.getSession()) {
            TOperationResult tuple = null;
            int resultCount = 0;

            final String queryString = getQueryString(state, operation);
            state.logQuery(operation.getClass().getSimpleName(), queryString);
            final Result result = session.run(queryString);
            if (result.hasNext()) {
                final Record record = result.next();
                resultCount++;

                tuple = convertSingleResult(record);

                if (state.isPrintResults()) {
                    System.out.println(tuple.toString());
                }
            }
            session.close();

            resultReporter.report(resultCount, tuple, operation);
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public abstract TOperationResult convertSingleResult(Record record) throws ParseException;

}
