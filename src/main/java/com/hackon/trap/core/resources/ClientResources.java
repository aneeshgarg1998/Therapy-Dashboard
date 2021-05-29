package com.hackon.trap.core.resources;

import com.hackon.trap.core.dtos.AddClientDTO;
import com.hackon.trap.core.dtos.AddClientNoteDTO;
import com.hackon.trap.core.dtos.ClientNotesDTO;
import com.hackon.trap.core.dtos.GetClientsDTO;
import com.hackon.trap.core.models.Client;
import com.hackon.trap.core.models.ClientNote;
import com.hackon.trap.core.models.ClientNotesRender;
import com.hackon.trap.core.services.ClientNoteServices;
import com.hackon.trap.core.services.ClientServices;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Path("/client")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ClientResources {

    private final ClientServices clientServices;
    private final ClientNoteServices clientNoteServices;

    public ClientResources (Jdbi jdbi){
        this.clientServices = new ClientServices(jdbi);
        this.clientNoteServices = new ClientNoteServices(jdbi);
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
            return Response.ok("Client added successfully!").build();
        }
        return Response.accepted("Client with email " + addClientDTO.getEmail() + " already exists !").build();
    }

    @GET
    @Path("/getAll")
    public Response getClients(GetClientsDTO getClientsDTO){
        getClientsDTO.setClientList(clientServices.getClients(getClientsDTO.getTherapistId()));
        return Response.ok(getClientsDTO).build();
    }

    @PUT
    @Path("/note/{id}")
    public Response addClientNote(@PathParam("id") Long clientId, AddClientNoteDTO addClientNoteDTO){
        if(clientServices.getClient(clientId) == null){
            return Response.accepted("Client doesn't exist").build();
        }
        ClientNote clientNote = ClientNote.builder()
                .clientId(clientId)
                .year(addClientNoteDTO.getYear())
                .month(addClientNoteDTO.getMonth())
                .date(addClientNoteDTO.getDate())
                .notes(addClientNoteDTO.getNote())
                .build();
        clientNoteServices.addClientNote(clientNote);
        return Response.ok("Note added successfully!").build();
    }

    @GET
    @Path("/notes/{id}")
    public Response getClientNotes(@PathParam("id") Long clientId) {
        Client client = clientServices.getClient(clientId);
        if(client == null){
            return Response.accepted("Client doesn't exist").build();
        }
        List<ClientNotesRender> clientNotesRenders = clientNoteServices.getClientNotes(clientId);
        ClientNotesDTO clientNotesDTO = new ClientNotesDTO();
        clientNotesDTO.setClient(client);
        clientNotesDTO.setNotes(clientNotesRenders);
        return Response.ok(clientNotesDTO).build();
    }
}
