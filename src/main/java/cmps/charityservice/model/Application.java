package cmps.charityservice.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Data
public class Application {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String authorId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String targetAmount;

    private String shortDescription;

    private String fullDescription;

    private String donationLink;

    private String phoneNumber;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<String> images;
}