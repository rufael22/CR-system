package registration.registrationsystem.service;

import registration.registrationsystem.domain.RegistrationEvent;
import registration.registrationsystem.service.dto.RegistrationEventDto;

import java.util.List;

public interface IRegistrationEventService {

    public void save(RegistrationEventDto eventDto);
    public void update(RegistrationEventDto eventDto);
    public List<RegistrationEventDto> findAll();
    public RegistrationEventDto findById(long id);

    List<RegistrationEvent> getRegistrationEvenByStudentId(long studentId);
    RegistrationEventDto getLatestRegistrationEvent();

    void processRegistrations(long id);
    public void sendEmail(long id);
}
