package registration.registrationsystem.Util;

import registration.registrationsystem.domain.CourseOffering;
import registration.registrationsystem.domain.Registration;
import registration.registrationsystem.domain.RegistrationEvent;
import registration.registrationsystem.domain.RegistrationRequest;


import java.time.LocalDateTime;
import java.util.List;

public class RegistrationSystemUtility {

    public static String getEventStatus(RegistrationEvent registrationEvent){
        LocalDateTime now = LocalDateTime.now();
        String  status = "";
        if (now.isBefore(registrationEvent.getEndDateTime()) && now.isAfter(registrationEvent.getStartDateTime())){
            status = "in progress";
        }
        else if (now.equals(registrationEvent.getStartDateTime())){
            status = "opened";
        }
        else if (now.isBefore(registrationEvent.getStartDateTime())) {
            status = "not opened";
        } else{
            status = "closed";
        }
        return status;
    }

    public static Registration convertToRegistration(List<RegistrationRequest> requests){
        for (RegistrationRequest request : requests)

            if (request.getCourseOffering().getAvailableSeats() > 0)
                return new Registration(request.getStudent(), request.getCourseOffering());
        return null;
    }


}
