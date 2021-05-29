package com.hackon.trap.core.daos;

import com.google.inject.ImplementedBy;
import com.hackon.trap.core.models.Client;

import java.util.List;

@ImplementedBy(ClientDAOImpl.class)
public interface ClientDAO {

    void addClient(Client client);

    Client getClient(Long clientId);

    Client getClient(String email);

    List<Client> getClients(Long therapistId);

}
