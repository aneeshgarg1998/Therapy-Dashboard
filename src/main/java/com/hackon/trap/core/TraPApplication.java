package com.hackon.trap.core;

import com.hackon.trap.core.resources.AuthResources;
import com.hackon.trap.core.resources.ClientResources;
import com.hackon.trap.core.resources.MeetingResources;
import com.hackon.trap.core.resources.TestResources;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TraPApplication extends Application<TraPConfig> {

    private static final Logger logger = LoggerFactory.getLogger(TraPApplication.class);

    public static void main(String[] args) throws Exception {
        new TraPApplication().run("server",args[0]);
    }

    @Override
    public void initialize(Bootstrap<TraPConfig> bootstrap){

    }

    @Override
    public void run(TraPConfig configuration, Environment environment) throws Exception {
        logger.info("Registering RESTful API resources");

        Jdbi jdbi = Jdbi.create(configuration.getDataSourceFactory().getUrl());
        environment.healthChecks().register("MySQL DB Health Check", new MySQLHealthCheckResource(jdbi));
        environment.healthChecks().register("TraPHealthCheck", new TraPHealthCheckResource(configuration));

        environment.jersey().register(new TestResources());
        environment.jersey().register(new AuthResources(jdbi));
        environment.jersey().register(new MeetingResources(jdbi));
        environment.jersey().register(new ClientResources(jdbi));
    }
}
