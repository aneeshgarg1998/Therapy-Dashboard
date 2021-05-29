package com.hackon.trap.core.resources;

import com.hackon.trap.core.dtos.AddClientDTO;
import com.hackon.trap.core.dtos.GetClientsDTO;
import com.hackon.trap.core.models.Client;
import com.hackon.trap.core.services.ClientServices;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/client")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ClientResources {

    private final ClientServices clientServices;

    public ClientResources (Jdbi jdbi){
        this.clientServices = new ClientServices(jdbi);
    }

    @PUT
    @Path("/add")
    public Response addClient(AddClientDTO addClientDTO){
        if(clientServices.getClient(addClientDTO.getEmail()) == null){
            clientServices.addClient(Client.builder()
                            .therapistId(addClientDTO.getTherapistId())
                            .birthDate(addClientDTO.getBirthDate())
                            .email(addClientDTO.getEmail())
                            .contactNumber(addClientDTO.getContactNumber())
                            .firstName(addClientDTO.getFirstName())
                            .lastName(addClientDTO.getLastName())
                            .build());
            return Response.ok().build();
        }
        return Response.accepted("Client with email " + addClientDTO.getEmail() + " already exists !").build();
    }

    @GET
    @Path("/getAll")
    public Response getClients(GetClientsDTO getClientsDTO){
        getClientsDTO.setClientList(clientServices.getClients(getClientsDTO.getTherapistId()));
        return Response.ok(getClientsDTO).build();
    }

}
