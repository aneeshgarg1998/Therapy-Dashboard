package com.hackon.trap.core.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement
public class UserLoginDTO {

    String email;
    String password;

}
