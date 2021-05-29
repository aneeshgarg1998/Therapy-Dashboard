package com.hackon.trap.core.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class AddClientNoteDTO {

    Long year;
    Long month;
    Long date;
    String title;
    String description;

}
