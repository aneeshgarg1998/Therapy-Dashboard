package com.hackon.trap.core.services;

import com.hackon.trap.core.daos.UserDAO;
import com.hackon.trap.core.daos.UserDAOImpl;
import com.hackon.trap.core.models.User;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Singleton;

@Singleton
@Slf4j
public class UserServices {

    private UserDAO userDAO;

    public UserServices(Jdbi jdbi){
        this.userDAO = new UserDAOImpl(jdbi);
    }

    public void addUser(User user){
        userDAO.insertUser(user);
    }

    public User getUser(String email){
        return userDAO.getUser(email);
    }

}
