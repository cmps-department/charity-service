package cmps.charityservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String authorId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String targetAmount;

    private String shortDescription;

    private String fullDescription;
}