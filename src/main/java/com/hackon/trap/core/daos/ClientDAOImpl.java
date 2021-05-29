package com.hackon.trap.core.daos;

import com.hackon.trap.Constants;
import com.hackon.trap.core.models.Client;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClientDAOImpl implements ClientDAO{

    private final Jdbi jdbi;

    public ClientDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
        jdbi.registerRowMapper(new ClientRowMapper());
    }

    @Override
    public void addClient(Client client) {
        jdbi.useHandle(handle -> {
            String query = "INSERT INTO CLIENT (THERAPIST_ID, FIRST_NAME, LAST_NAME, EMAIL, CONTACT_NUMBER, BIRTH_DATE) " +
                    "VALUES (:therapistId, :firstName, :lastName, :email, :contactNumber, :birthDate)";
            handle.createUpdate(query)
                    .bind("therapistId", client.getTherapistId())
                    .bind("firstName", client.getFirstName())
                    .bind("lastName", client.getLastName())
                    .bind("email", client.getEmail())
                    .bind("contactNumber", client.getContactNumber())
                    .bind("birthDate", client.getBirthDate())
                    .execute();
        });
    }

    @Override
    public Client getClient(Long clientId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM CLIENT WHERE id = :id")
                .bind("id", clientId).mapTo(Client.class).findFirst().orElse(null));
    }

    @Override
    public Client getClient(String email) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM CLIENT WHERE email = :email")
                .bind("email", email).mapTo(Client.class).findFirst().orElse(null));
    }

    @Override
    public List<Client> getClients(Long therapistId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM CLIENT WHERE THERAPIST_ID = :therapistId")
                .bind("therapistId", therapistId).mapTo(Client.class).list());
    }

    private static class ClientRowMapper implements RowMapper<Client> {

        @Override
        public Client map(ResultSet rs, StatementContext ctx) throws SQLException {
            return Client.builder()
                    .id(rs.getLong(Constants.FIELD_ID))
                    .therapistId(rs.getLong(Constants.FIELD_THERAPIST_ID))
                    .firstName(rs.getString(Constants.FIELD_FIRST_NAME))
                    .lastName(rs.getString(Constants.FIELD_LAST_NAME))
                    .birthDate(rs.getString(Constants.FIELD_BIRTH_DATE))
                    .email(rs.getString(Constants.FIELD_EMAIL))
                    .contactNumber(rs.getString(Constants.FIELD_CONTACT_NUMBER))
                    .build();
        }
    }

}
