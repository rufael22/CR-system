package registration.registrationsystem.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseOffering {
    @Id
//    @GeneratedValue
    long id;
    String code;
    String name;
    int capacity;
    int availableSeats;

//    @ManyToOne
//    @JoinColumn(name = "blockId")
//    private AcademicBlock academicBlock;

     @ManyToOne
     private Course course;

    @OneToOne
    private Faculty faculty;

    public void computeAvailableSeat(int numberOfStudentRegistered){
        availableSeats = capacity - numberOfStudentRegistered;
    }
}
