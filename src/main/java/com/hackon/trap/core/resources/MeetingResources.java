package com.hackon.trap.core.resources;

import com.hackon.trap.core.dtos.MeetingRequestDTO;
import com.hackon.trap.core.dtos.MeetingScheduleDTO;
import com.hackon.trap.core.models.Client;
import com.hackon.trap.core.models.MeetingSchedule;
import com.hackon.trap.core.services.ClientServices;
import com.hackon.trap.core.services.MeetingServices;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Path("/meetings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MeetingResources {

    private final MeetingServices meetingServices;
    private final ClientServices clientServices;

    public MeetingResources(Jdbi jdbi){
        this.meetingServices = new MeetingServices(jdbi);
        this.clientServices = new ClientServices(jdbi);
    }

    @GET
    @Path("/getAll")
    public Response getAllMeetings(MeetingRequestDTO meetingRequestDTO){
        meetingRequestDTO.setSchedules(meetingServices.getMeetings(meetingRequestDTO.getTherapistId()));
        return Response.ok(meetingRequestDTO).build();
    }

    @PUT
    @Path("/schedule")
    public Response scheduleMeeting(MeetingScheduleDTO meetingScheduleDTO){
        Client client = clientServices.getClient(meetingScheduleDTO.getClientEmail());
        if(client == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        meetingServices.scheduleMeeting(MeetingSchedule.builder()
                .therapistId(meetingScheduleDTO.getTherapistId())
                .clientId(client.getId())
                .toDo(meetingScheduleDTO.getToDo())
                .duration(meetingScheduleDTO.getDuration())
                .when(meetingScheduleDTO.getWhen())
                .clientName(meetingScheduleDTO.getClientName())
                .build());
        return Response.ok("Meeting scheduled successfully!").build();

    }

}
