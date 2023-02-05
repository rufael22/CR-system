package registration.registrationsystem.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
//    @GeneratedValue
    long id;
    @Column(unique = true)
    String code;
    String name;
    String description;

    @ManyToMany
    List<Course> prerequisites;

}


