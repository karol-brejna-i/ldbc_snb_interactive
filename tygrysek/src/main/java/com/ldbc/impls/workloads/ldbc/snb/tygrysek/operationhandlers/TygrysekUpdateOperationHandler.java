package com.ldbc.impls.workloads.ldbc.snb.tygrysek.operationhandlers;

import com.ldbc.driver.DbException;
import com.ldbc.driver.Operation;
import com.ldbc.driver.ResultReporter;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcNoResult;
import com.ldbc.impls.workloads.ldbc.snb.operationhandlers.UpdateOperationHandler;
import com.ldbc.impls.workloads.ldbc.snb.tygrysek.TygrysekDbConnectionState;
import org.neo4j.driver.Session;

public abstract class TygrysekUpdateOperationHandler<TOperation extends Operation<LdbcNoResult>>
        implements UpdateOperationHandler<TOperation, TygrysekDbConnectionState> {

    @Override
    public void executeOperation(TOperation operation, TygrysekDbConnectionState state,
                                 ResultReporter resultReporter) throws DbException {
        try (Session session = state.getSession()) {
            final String queryString = getQueryString(state, operation);
            state.logQuery(operation.getClass().getSimpleName(), queryString);
            session.run(queryString);
        } catch (Exception e) {
            throw new DbException(e);
        }
        resultReporter.report(0, LdbcNoResult.INSTANCE, operation);
    }
}
