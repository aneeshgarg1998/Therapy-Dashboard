package com.hackon.trap.core.services;

import com.hackon.trap.core.daos.ClientNotesDAO;
import com.hackon.trap.core.daos.ClientNotesDAOImpl;
import com.hackon.trap.core.models.ClientNote;
import com.hackon.trap.core.models.ClientNotesRender;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Slf4j
public class ClientNoteServices {

    private final ClientNotesDAO clientNotesDAO;

    public ClientNoteServices (Jdbi jdbi){
        this.clientNotesDAO = new ClientNotesDAOImpl(jdbi);
    }

    public void addClientNote(ClientNote clientNote){
        clientNotesDAO.addClientNote(clientNote);
    }

    public List<ClientNotesRender> getClientNotes(Long clientId){
        List<ClientNote> clientNotes = clientNotesDAO.getClientNotes(clientId);
        List<ClientNotesRender> clientNotesRenders = new ArrayList<>();
        Long year = null;
        for(ClientNote clientNote : clientNotes){
            if(!clientNote.getYear().equals(year)){
                year = clientNote.getYear();
                ClientNotesRender clientNotesRender = ClientNotesRender.builder()
                        .year(year)
                        .notes(new ArrayList<>())
                        .build();
                clientNotesRender.getNotes().add(clientNote);
                clientNotesRenders.add(clientNotesRender);
            }
            else{
                clientNotesRenders.get(clientNotesRenders.size()-1).getNotes().add(clientNote);
            }
        }
        return clientNotesRenders;
    }

}
