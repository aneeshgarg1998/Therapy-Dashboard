package com.hackon.trap.core.resources;

import com.hackon.trap.core.dtos.UserLoginDTO;
import com.hackon.trap.core.dtos.UserSignupRequestDTO;
import com.hackon.trap.core.models.User;
import com.hackon.trap.core.services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/auth")
public class AuthResources {

    private final UserServices userServices;

    public AuthResources(Jdbi jdbi){
        this.userServices = new UserServices(jdbi);
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response signup(UserSignupRequestDTO userSignupDTO) {
        if(userServices.getUser(userSignupDTO.getEmail()) != null){
            return Response.accepted("User already exists").build();
        }

        //ToDO: Password encrypt
        User user = User.builder()
                .displayName(userSignupDTO.getFirstName() + " " + userSignupDTO.getLastName())
                .firstName(userSignupDTO.getFirstName())
                .lastName(userSignupDTO.getLastName())
                .email(userSignupDTO.getEmail())
                .password(userSignupDTO.getPassword())
                .build();

        userServices.addUser(user);

        return Response.ok().build();
    }

    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response authenticate(UserLoginDTO userLoginDTO) {
        User user = userServices.getUser(userLoginDTO.getEmail());
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(user.getPassword().equals(userLoginDTO.getPassword())){
            return Response.ok(user.getId()).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

}
