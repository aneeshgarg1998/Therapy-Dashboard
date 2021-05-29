package com.hackon.trap.core;

import com.codahale.metrics.health.HealthCheck;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class MySQLHealthCheckResource extends HealthCheck {

    private final Jdbi jdbi;

    public MySQLHealthCheckResource(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    protected Result check() throws Exception {
        try(Handle handle = jdbi.open()){
            if(handle.createQuery("/* ping */ SELECT 1").mapTo(Integer.class).first() != null){
                return Result.healthy();
            } else {
                return Result.unhealthy("Can not connect to MySQL database");
            }
        }
    }
}
