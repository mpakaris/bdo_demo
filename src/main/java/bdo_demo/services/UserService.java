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

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public Task createTaskForUser(Long userId, Task task) {
        User user = getUserById(userId);
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return (User) userRepository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        User user = getUserById(userId);
        // Update user fields here
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        user.setPasswordHash(updatedUser.getPasswordHash());
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).get();
        // Soft delete all associated tasks
        for (Task task : user.getTasks()) {
            task.setDeleted(true);
            // Save the task to mark it as "deleted" in the database
            taskRepository.save(task);
        }
        // Soft delete the user
        user.setDeleted(true);

        userRepository.save(user);
    }
}
