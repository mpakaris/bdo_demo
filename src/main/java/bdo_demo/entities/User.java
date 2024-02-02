package bdo_demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Where(clause = "deleted = false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Embedded
    private Address address;
    private String passwordHash;
    private boolean deleted = false;
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<Task> tasks;
}
