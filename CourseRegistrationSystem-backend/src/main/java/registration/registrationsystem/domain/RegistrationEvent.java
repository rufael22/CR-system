package registration.registrationsystem.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationEvent {
    @Id
//    @GeneratedValue
    long id;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;

    @OneToMany(fetch = FetchType.EAGER)
    private List<RegistrationGroup> registrationGroups = new ArrayList<>();

    public RegistrationEvent(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
