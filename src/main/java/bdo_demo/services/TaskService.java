package bdo_demo.services;

import bdo_demo.entities.Task;
import bdo_demo.entities.User;
import bdo_demo.exceptions.ResourceNotFoundException;
import bdo_demo.repository.TaskRepository;
import bdo_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public Task createTask(Long userId, Task task) {
        User user = userService.getUserById(userId);
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<Task> getTasks() {
        return taskRepository.findByDeletedIsFalse();
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findByIdAndDeletedIsFalse(taskId);
        if (taskOptional.isPresent()) {
            return taskOptional.get();
        } else {
            throw new ResourceNotFoundException("Task not found with id: " + taskId);
        }
    }

    @Transactional
    public Task updateTask(Long taskId, Task updatedTask) {
        // Fetch the existing task from the database
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
        if (updatedTask.getUser() != null) {
            User newUser = userRepository.findById(updatedTask.getUser().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + updatedTask.getUser().getId()));

            existingTask.getUser().getTasks().remove(existingTask);
            existingTask.setUser(newUser);
            newUser.getTasks().add(existingTask);
        }
        if (updatedTask.getTaskTitle() != null) {
            existingTask.setTaskTitle(updatedTask.getTaskTitle());
        }
        if (updatedTask.getTaskDescription() != null) {
            existingTask.setTaskDescription(updatedTask.getTaskDescription());
        }
        return taskRepository.save(existingTask);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        Task task = getTaskById(taskId);
        task.setDeleted(true);
        taskRepository.save(task);
    }
}
