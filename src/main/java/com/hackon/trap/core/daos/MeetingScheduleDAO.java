package com.hackon.trap.core.daos;

import com.google.inject.ImplementedBy;
import com.hackon.trap.core.models.MeetingSchedule;

import java.util.List;

@ImplementedBy(MeetingScheduleDAOImpl.class)
public interface MeetingScheduleDAO {

    void addMeetingSchedule(MeetingSchedule meetingSchedules);

    List<MeetingSchedule> getMeetingSchedules(Long therapistId);

}
