package com.hackon.trap.core.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class UserSignupRequestDTO {

    String firstName;
    String lastName;
    String email;
    String password;

}
