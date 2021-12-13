package com.ldbc.impls.workloads.ldbc.snb.tygrysek.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;

import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.ListOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tygrysek.TygrysekDbConnectionState;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class TygrysekListOperationHandler<TOperation extends Operation<List<TOperationResult>>, TOperationResult>
        implements ListOperationHandler<TOperationResult, TOperation, TygrysekDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, TygrysekDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        Session session = state.getSession();
        List<TOperationResult> results = new ArrayList<>();
        int resultCount = 0;
        results.clear();

        final String queryString = getQueryString(state, operation);
        state.logQuery(operation.getClass().getSimpleName(), queryString);
        final Result result = session.run(queryString);
        while (result.hasNext()) {
            final Record record = result.next();

            resultCount++;
            TOperationResult tuple;
            try {
                tuple = convertSingleResult(record);
            } catch (ParseException e) {
                throw new DbException(e);
            }
            if (state.isPrintResults()) {
                System.out.println(tuple.toString());
            }
            results.add(tuple);
        }
        session.close();
        resultReporter.report(resultCount, results, operation);
    }

    public abstract TOperationResult convertSingleResult(Record record) throws ParseException;

}
