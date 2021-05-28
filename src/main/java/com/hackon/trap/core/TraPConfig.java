package com.hackon.trap.core;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TraPConfig extends Configuration {

    @JsonProperty
    public String appName;

    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

}
