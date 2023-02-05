package registration.registrationsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import registration.registrationsystem.service.dto.RegistrationResponseDto;
import registration.registrationsystem.service.impl.RegistrationService;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @GetMapping("/{studentId}")
    public List<RegistrationResponseDto> getAll(@PathVariable long studentId){
        return registrationService.findAllById(studentId);
    }
}
