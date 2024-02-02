package bdo_demo.entitiesDTO;
import lombok.Data;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Set<TaskDTO> tasks;
}

