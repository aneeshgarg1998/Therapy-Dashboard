package com.hackon.trap.core;

import com.hackon.trap.core.resources.TestResources;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

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
        logger.info("App: %s", configuration.getAppName());

        final DataSource dataSource = configuration.getDataSourceFactory().build(environment.metrics(), "sql");
        DBI dbi = new DBI(dataSource);

        environment.healthChecks().register("MySQL DB Health Check", new MySQLHealthCheckResource(dbi));
        environment.healthChecks().register("TraPHealthCheck", new TraPHealthCheckResource(configuration));

        environment.jersey().register(new TestResources());
    }
}
