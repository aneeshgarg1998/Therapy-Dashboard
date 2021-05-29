package com.hackon.trap.core.dtos;

import com.hackon.trap.core.models.Client;
import com.hackon.trap.core.models.ClientNotesRender;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement
public class ClientNotesDTO {

    Client client;
    List<ClientNotesRender> notes;

}
