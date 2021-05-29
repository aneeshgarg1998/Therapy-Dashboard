package com.hackon.trap.core.services;

import com.hackon.trap.core.daos.MeetingScheduleDAO;
import com.hackon.trap.core.daos.MeetingScheduleDAOImpl;
import com.hackon.trap.core.models.MeetingSchedule;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Singleton;
import java.util.List;

@Singleton
@Slf4j
public class MeetingServices {

    private final MeetingScheduleDAO meetingScheduleDAO;

    public MeetingServices(Jdbi jdbi){
        this.meetingScheduleDAO = new MeetingScheduleDAOImpl(jdbi);
    }

    public List<MeetingSchedule> getMeetings(Long therapistId){
        return meetingScheduleDAO.getMeetingSchedules(therapistId);
    }

    public void scheduleMeeting(MeetingSchedule meetingSchedule){
        meetingScheduleDAO.addMeetingSchedule(meetingSchedule);
    }

}
