package registration.registrationsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import registration.registrationsystem.Util.RegistrationSystemUtility;
import registration.registrationsystem.domain.*;
import registration.registrationsystem.repository.RegistrationEventRepository;
import registration.registrationsystem.repository.RegistrationRequestRepository;
import registration.registrationsystem.repository.StudentRepository;
import registration.registrationsystem.service.IRegistrationRequestService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RegistrationRequestService implements IRegistrationRequestService {

    @Autowired
    RegistrationEventRepository registrationEventRepository;

    @Autowired
    RegistrationRequestRepository registrationRequestRepository;
    @Autowired
    StudentRepository studentRepository;
    @Override
//    public String save(List<RegistrationRequest> registrationRequests) {
//        Set<Long> offeringIds = new HashSet<>();
//        RegistrationEvent latest = registrationEventRepository.findFirstByOrderByStartDateTimeDesc();
//        String status = RegistrationSystemUtility.getEventStatus(latest);
//        if(status.equalsIgnoreCase("in progress") || status.equalsIgnoreCase("opened")){
//            List<RegistrationGroup> groups = latest.getRegistrationGroups();
//            //load course offerings
//            groups.stream().map(g -> g.getAcademicBlocks().stream().map(a -> a.getCourseOfferings().stream().map(c -> offeringIds.add(c.getId()))));
//            //save registration request
//            registrationRequests.stream().filter(r -> offeringIds.contains(r.getCourseOffering().getId())).map(r -> registrationRequestRepository.save(r));
//            return "saved";
//        } else {
//            return "eventClosed";
//        }
//    }
    public ResponseEntity<String> save(List<RegistrationRequest> registrationRequests, long studentId) {
        Set<Long> offeringIds = new HashSet<>();
        Student student = studentRepository.findById(studentId).get();
        RegistrationEvent latest = registrationEventRepository.findFirstByOrderByStartDateTimeDesc();
        String status = RegistrationSystemUtility.getEventStatus(latest);
        if(status.equalsIgnoreCase("in progress") || status.equalsIgnoreCase("opened")){
            List<RegistrationGroup> groups = latest.getRegistrationGroups();
            for(RegistrationGroup group : groups){
                List<AcademicBlock> blocks = group.getAcademicBlocks();
                for(AcademicBlock block : blocks){
                    List<CourseOffering> courseOfferings = block.getCourseOfferings();
                    for(CourseOffering c: courseOfferings){
                        offeringIds.add(c.getId());
                    }
                }
            }
            boolean saved = false;
            for(RegistrationRequest request : registrationRequests){
                if(offeringIds.contains(request.getCourseOffering().getId())){
                    request.setStudent(student);
                    registrationRequestRepository.save(request);
                    System.out.println("saved");
                    saved = true;
                }
            }
            if(saved){
                return ResponseEntity.ok("Successfully saved!");
            }
            else{
                return ResponseEntity.ok("The course that you requested is not currently offered");
            }
        }
        return ResponseEntity.ok("Registration Event is either not opened or closed");
    }
}
