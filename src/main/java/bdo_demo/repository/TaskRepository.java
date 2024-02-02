package bdo_demo.repository;

import bdo_demo.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDeletedIsFalse();
    Optional<Task> findByIdAndDeletedIsFalse(Long taskId);
}
