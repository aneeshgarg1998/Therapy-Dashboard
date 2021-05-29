package com.hackon.trap.core.dtos;

import com.hackon.trap.core.models.MeetingSchedule;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement
public class MeetingRequestDTO {

    Long therapistId;
    List<MeetingSchedule> schedules;

}
