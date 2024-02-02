package bdo_demo.entitiesDTO;
import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private UserDTO user;
}
