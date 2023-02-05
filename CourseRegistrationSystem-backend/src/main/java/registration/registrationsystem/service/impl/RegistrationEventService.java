package registration.registrationsystem.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import registration.registrationsystem.Integration.Sender;
import registration.registrationsystem.Util.RegistrationSystemUtility;
import registration.registrationsystem.domain.*;
import registration.registrationsystem.repository.CourseOfferingRepository;
import registration.registrationsystem.repository.RegistrationEventRepository;
import registration.registrationsystem.repository.RegistrationRepository;
import registration.registrationsystem.repository.StudentRepository;
import registration.registrationsystem.service.IRegistrationEventService;
import registration.registrationsystem.Util.ListMapper;
import registration.registrationsystem.service.dto.RegistrationEventDto;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RegistrationEventService implements IRegistrationEventService {

    @Autowired
    ListMapper<RegistrationEvent, RegistrationEventDto> listMapper;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    RegistrationRepository registrationRepository;
    @Autowired
    CourseOfferingRepository courseOfferingRepository;

    @Autowired
    RegistrationEventRepository registrationEventRepository;
    @Autowired
    Sender  sender;

    @Override
    public RegistrationEventDto getLatestRegistrationEvent() {
        RegistrationEvent latest = registrationEventRepository.findFirstByOrderByStartDateTimeDesc();
        RegistrationEventDto latestDto =  listMapper.mapClassToDto(latest, new RegistrationEventDto());
         latestDto.setStatus(RegistrationSystemUtility.getEventStatus(latest));
        return latestDto;
    }

    @Override
    public void processRegistrations(long id) {
        Optional<RegistrationEvent> optional = registrationEventRepository.findById(id);
        if (optional.isPresent()) {
            RegistrationEvent registrationEvent = optional.get();
            List<RegistrationGroup> groups = registrationEvent.getRegistrationGroups();

            groups.forEach(group -> group.getStudents().forEach(s -> {
                List<RegistrationRequest> registrationRequests = s.getRegistrationRequests();
                Registration registration = RegistrationSystemUtility.convertToRegistration(registrationRequests);
                if (registration != null) {
                    registrationRepository.save(registration);
                    CourseOffering courseOffering = registration.getCourseOffering();
                    int size = registrationRepository.findAll().size();
                    courseOffering.computeAvailableSeat(size);
                    courseOfferingRepository.updateAvailableSeats(courseOffering.getAvailableSeats());
                } else
                    throw new RuntimeException("there is no enough capacity");
            })
            );
        }else
            System.out.println("event not found");
    }
    @Override
    public void save(RegistrationEventDto eventDto) {
        registrationEventRepository.save(listMapper.mapClassFromDto(eventDto, new RegistrationEvent()));
    }

    @Override
    public void sendEmail(long id){
        Optional<RegistrationEvent> optional = registrationEventRepository.findById(id);
        if (optional.isPresent()) {
            RegistrationEvent   registrationEvent = optional.get();
            List<RegistrationGroup> groups = registrationEvent.getRegistrationGroups();
            List<String> emails = new ArrayList<>();
            List<String> names = new ArrayList<>();
            groups.forEach(g -> g.getStudents().forEach(student -> {
                emails.add(student.getEmail());
                names.add(student.getName());
            }));
            System.out.println(names + " " + emails);
            EmailInfo emailMessage = new EmailInfo(emails, names, registrationEvent.getStartDateTime().toString(), registrationEvent.getEndDateTime().toString());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String eventString = objectMapper.writeValueAsString(emailMessage);
                sender.send("eventTopic", eventString);
                System.out.println("message sending");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else
            System.out.println("event not found");
    }

    @Override
    public void update(RegistrationEventDto eventDto) {
        RegistrationEvent eventToBeUpdated = registrationEventRepository.findById(eventDto.getId()).get();
        eventToBeUpdated.setEndDateTime(eventDto.getEndDateTime());
        eventToBeUpdated.setStartDateTime(eventDto.getStartDateTime());
        registrationEventRepository.save(eventToBeUpdated);
    }

    @Override
    public List<RegistrationEventDto> findAll() {
        List<RegistrationEvent> registrationEvents = registrationEventRepository.findAll();
        List<RegistrationEventDto> eventDtos = new ArrayList<>();
        for(RegistrationEvent registrationEvent : registrationEvents){
            RegistrationSystemUtility.getEventStatus(registrationEvent);
            RegistrationEventDto dto = listMapper.mapClassToDto(registrationEvent, new RegistrationEventDto());
            eventDtos.add(dto);
        }
        return eventDtos;
    }

    @Override
    public RegistrationEventDto findById(long id) {
        return listMapper.mapClassToDto(registrationEventRepository.findById(id).get(), new RegistrationEventDto());
    }

    @Override
    public List<RegistrationEvent> getRegistrationEvenByStudentId(long studentId) {
        List<RegistrationEvent> events =  registrationEventRepository.getRegistrationEvenByStudentId(studentId);
        return events;
    }

}
