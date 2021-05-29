package com.hackon.trap.core.daos;

import com.hackon.trap.Constants;
import com.hackon.trap.core.models.MeetingSchedule;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MeetingScheduleDAOImpl implements MeetingScheduleDAO{

    private final Jdbi jdbi;

    public MeetingScheduleDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
        jdbi.registerRowMapper(new MeetingScheduleRowMapper());
    }

    @Override
    public void addMeetingSchedule(MeetingSchedule meetingSchedules) {
        jdbi.useHandle(handle -> {
            String query = "INSERT INTO `MEETING_SCHEDULES` (`THERAPIST_ID`, `CLIENT_ID`, `WHEN`, `DURATION`, `TODO`, `CLIENT_NAME`)  " +
                    "VALUES (:therapistId, :clientId, :when, :duration, :toDo, :clientName)";
            handle.createUpdate(query)
                    .bind("therapistId", meetingSchedules.getTherapistId())
                    .bind("clientId", meetingSchedules.getClientId())
                    .bind("when", meetingSchedules.getWhen())
                    .bind("duration", meetingSchedules.getDuration())
                    .bind("toDo", meetingSchedules.getToDo())
                    .bind("clientName", meetingSchedules.getClientName())
                    .execute();
        });
    }

    @Override
    public List<MeetingSchedule> getMeetingSchedules(Long therapistId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM `MEETING_SCHEDULES` WHERE THERAPIST_ID = :therapistId ORDER BY `WHEN`")
                .bind("therapistId", therapistId).mapTo(MeetingSchedule.class).list());
    }

    private static class MeetingScheduleRowMapper implements RowMapper<MeetingSchedule> {

        @Override
        public MeetingSchedule map(ResultSet rs, StatementContext ctx) throws SQLException {
            return MeetingSchedule.builder()
                    .id(rs.getLong(Constants.FIELD_ID))
                    .clientId(rs.getLong(Constants.FIELD_CLIENT_ID))
                    .therapistId(rs.getLong(Constants.FIELD_THERAPIST_ID))
                    .toDo(rs.getString(Constants.FIELD_TODO))
                    .when(rs.getString(Constants.FIELD_WHEN))
                    .duration(rs.getLong(Constants.FIELD_DURATION))
                    .clientName(rs.getString(Constants.FIELD_CLIENT_NAME))
                    .build();
        }
    }
}
