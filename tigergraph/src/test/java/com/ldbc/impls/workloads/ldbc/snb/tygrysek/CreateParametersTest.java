package com.ldbc.impls.workloads.ldbc.snb.tygrysek;

import com.google.common.collect.ImmutableList;
import com.ldbc.driver.ClientException;
import com.ldbc.driver.workloads.ldbc.snb.interactive.LdbcUpdate1AddPerson;
import com.ldbc.impls.workloads.ldbc.snb.tygrysek.connector.ResultConverter;
import junit.framework.TestCase;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CreateParametersTest extends TestCase {

    public void test1() throws ParseException, ClientException {
        com.ldbc.driver.Client.main(new String[]{"-P", "driver/create-validation-parameters.properties"});
    }
}