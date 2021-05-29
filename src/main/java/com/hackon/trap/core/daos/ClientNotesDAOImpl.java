package com.hackon.trap.core.daos;

import com.hackon.trap.Constants;
import com.hackon.trap.core.models.ClientNote;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClientNotesDAOImpl implements ClientNotesDAO{

    private final Jdbi jdbi;

    public ClientNotesDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
        jdbi.registerRowMapper(new ClientNotesRowMapper());
    }


    @Override
    public void addClientNote(ClientNote clientNote) {
        jdbi.useHandle(handle -> {
            String query = getAddClientQuery(clientNote);
            if(clientNote.getDate() == null){
                handle.createUpdate(query)
                        .bind("clientId", clientNote.getClientId())
                        .bind("year", clientNote.getYear())
                        .bind("month", clientNote.getMonth())
                        .bind("notes", clientNote.getNotes())
                        .execute();
            }
            else{
                handle.createUpdate(query)
                        .bind("clientId", clientNote.getClientId())
                        .bind("year", clientNote.getYear())
                        .bind("month", clientNote.getMonth())
                        .bind("notes", clientNote.getNotes())
                        .bind("date", clientNote.getDate())
                        .execute();
            }
        });
    }

    private String getAddClientQuery(ClientNote clientNote){
        String query = "INSERT INTO `CLIENT_NOTES` (`CLIENT_ID`, `YEAR`, `MONTH`, `NOTES` ";
        if(clientNote.getDate() != null){
            query += ",`DATE` ";
        }
        query += ") VALUES (:clientId, :year, :month, :notes";
        if(clientNote.getDate() != null){
            query += ", :date ";
        }
        query += ")";
        return query;
    }

    @Override
    public List<ClientNote> getClientNotes(Long clientId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM `CLIENT_NOTES` WHERE CLIENT_ID = :clientId ORDER BY `YEAR` DESC, `MONTH`, `DATE`")
                .bind("clientId", clientId).mapTo(ClientNote.class).list());
    }

    private static class ClientNotesRowMapper implements RowMapper<ClientNote>{

        @Override
        public ClientNote map(ResultSet rs, StatementContext ctx) throws SQLException {
            return ClientNote.builder()
                    .id(rs.getLong(Constants.FIELD_ID))
                    .clientId(rs.getLong(Constants.FIELD_CLIENT_ID))
                    .year(rs.getLong(Constants.FIELD_YEAR))
                    .month(rs.getLong(Constants.FIELD_MONTH))
                    .date(rs.getLong(Constants.FIELD_DATE))
                    .notes(rs.getString(Constants.FIELD_NOTES))
                    .build();
        }
    }
}
