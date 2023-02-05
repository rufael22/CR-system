package registration.registrationsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import registration.registrationsystem.domain.AcademicBlock;
import registration.registrationsystem.domain.CourseOffering;
import registration.registrationsystem.domain.RegistrationRequest;
import registration.registrationsystem.repository.AcademicBlockRepository;
import registration.registrationsystem.repository.CourseOfferingRepository;
import registration.registrationsystem.service.IRegistrationRequestService;

import java.util.List;

@RestController
@RequestMapping("/registration-requests")
public class RegistrationRequestController {

    @Autowired
    IRegistrationRequestService requestService;

    @PostMapping("{studentId}")
    public ResponseEntity<String> saveRequest(@RequestBody List<RegistrationRequest> registrationRequests, @PathVariable long studentId) {
        return requestService.save(registrationRequests, studentId);
    }
}
