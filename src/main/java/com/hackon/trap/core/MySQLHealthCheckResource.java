package com.hackon.trap.core;

import com.codahale.metrics.health.HealthCheck;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public class MySQLHealthCheckResource extends HealthCheck {

    private final DBI dbi;

    public MySQLHealthCheckResource(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    protected Result check() throws Exception {
        try(Handle handle = dbi.open()){
            if(handle.createQuery("/* ping */ SELECT 1").first() != null){
                return Result.healthy();
            } else {
                return Result.unhealthy("Can not connect to MySQL database");
            }
        }
    }
}
