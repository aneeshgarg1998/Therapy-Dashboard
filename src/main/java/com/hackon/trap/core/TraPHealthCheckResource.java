package com.hackon.trap.core;

import com.codahale.metrics.health.HealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraPHealthCheckResource extends HealthCheck {

    private static final Logger logger = LoggerFactory.getLogger(TraPHealthCheckResource.class);

    private static String appName;

    public TraPHealthCheckResource(TraPConfig traPConfig){
        this.appName = traPConfig.getAppName();
    }

    @Override
    protected Result check() throws Exception {
        logger.info("App name is: {}", this.appName);
        if("TheraPlanner".equalsIgnoreCase(this.appName)){
            return Result.healthy();
        }
        return Result.unhealthy("Therapy Planner is down.");
    }
}
