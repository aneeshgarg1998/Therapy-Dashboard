package com.hackon.trap.core.daos;

import com.google.inject.ImplementedBy;
import com.hackon.trap.core.models.User;

@ImplementedBy(UserDAOImpl.class)
public interface UserDAO {

    void insertUser(User user);

    User getUser(String email);

}
