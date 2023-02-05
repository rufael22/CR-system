package registration.registrationsystem.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RegistrationRequest {

    @Id
//    @GeneratedValue
    private long id;
    private int priorityNumber;

    @OneToOne
    CourseOffering courseOffering;

    @ManyToOne(fetch = FetchType.LAZY)
    Student student;
}
