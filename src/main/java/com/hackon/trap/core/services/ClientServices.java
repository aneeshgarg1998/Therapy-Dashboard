package com.hackon.trap.core.services;

import com.hackon.trap.core.daos.ClientDAO;
import com.hackon.trap.core.daos.ClientDAOImpl;
import com.hackon.trap.core.models.Client;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Singleton;
import java.util.List;

@Singleton
@Slf4j
public class ClientServices {

    private final ClientDAO clientDAO;

    public ClientServices(Jdbi jdbi){
        this.clientDAO = new ClientDAOImpl(jdbi);
    }

    public void addClient(Client client){
        clientDAO.addClient(client);
    }

    public Client getClient(Long clientId){
        return clientDAO.getClient(clientId);
    }

    public Client getClient(String email){
        return clientDAO.getClient(email);
    }

    public List<Client> getClients(Long therapistId){
        return clientDAO.getClients(therapistId);
    }

}
