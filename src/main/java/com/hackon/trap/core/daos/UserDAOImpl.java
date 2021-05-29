package com.hackon.trap.core.daos;

import com.hackon.trap.Constants;
import com.hackon.trap.core.models.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO{

    private final Jdbi jdbi;

    public UserDAOImpl(Jdbi jdbi) {
        this.jdbi = jdbi;
        jdbi.registerRowMapper(new UserRowMapper());
    }

    @Override
    public void insertUser(User user) {
        jdbi.useHandle(handle -> {
            String query = "INSERT INTO `USER` (`DISPLAY_NAME`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`) " +
                    "VALUES (:displayName, :firstName, :lastName, :email, :password)";
            handle.createUpdate(query)
                    .bind("displayName", user.getDisplayName())
                    .bind("firstName", user.getFirstName())
                    .bind("lastName", user.getLastName())
                    .bind("email", user.getEmail())
                    .bind("password", user.getPassword())
                    .execute();
        });
    }

    @Override
    public User getUser(String email) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM USER WHERE EMAIL = :email")
                    .bind("email", email).mapTo(User.class).findFirst().orElse(null));
    }

    private static class UserRowMapper implements RowMapper<User>{

        @Override
        public User map(ResultSet rs, StatementContext ctx) throws SQLException {
            return User.builder()
                    .id(rs.getLong(Constants.FIELD_ID))
                    .displayName(rs.getString(Constants.FIELD_DISPLAY_NAME))
                    .firstName(rs.getString(Constants.FIELD_FIRST_NAME))
                    .lastName(rs.getString(Constants.FIELD_LAST_NAME))
                    .email(rs.getString(Constants.FIELD_EMAIL))
                    .password(rs.getString(Constants.FIELD_PASSWORD))
                    .build();
        }
    }
}
