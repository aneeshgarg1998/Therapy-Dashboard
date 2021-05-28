package com.hackon.trap.core.resources;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class TestResources {

    @GET
    public Response ping(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "pong");
        return Response.ok(response).build();
    }

}
