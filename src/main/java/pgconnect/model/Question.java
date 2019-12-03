package pgconnect.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "questions")
public class Question extends AuditModel {

    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_sequence",
            initialValue = 1000
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public void setId(Long Id) { id = Id; }

    public void setTitle(String Title) { title = Title; }

    public void setDescription(String Description) { description = Description; }
}
