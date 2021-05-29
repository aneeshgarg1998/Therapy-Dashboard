package com.hackon.trap.core.daos;

import com.google.inject.ImplementedBy;
import com.hackon.trap.core.models.ClientNote;

import java.util.List;

@ImplementedBy(ClientNotesDAOImpl.class)
public interface ClientNotesDAO {

    void addClientNote(ClientNote clientNote);

    List<ClientNote> getClientNotes (Long clientId);

}
