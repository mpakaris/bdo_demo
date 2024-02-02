package bdo_demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "tasks")
@Where(clause = "deleted = false")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskTitle;
    private String taskDescription;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JsonIgnoreProperties("tasks")
    private User user;
    private boolean deleted = false;
}
