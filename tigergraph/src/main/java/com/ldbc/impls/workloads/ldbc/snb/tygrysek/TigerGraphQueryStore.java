package com.ldbc.impls.workloads.ldbc.snb.tygrysek;

import com.ldbc.impls.workloads.ldbc.snb.QueryStore;
import com.ldbc.driver.DbException;

public class TigerGraphQueryStore extends QueryStore{

        public TigerGraphQueryStore(String path) throws DbException {
            super(path, ".gsql");
        }

}
