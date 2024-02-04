package bdo_demo.services;

import bdo_demo.entities.Task;
import bdo_demo.entities.User;
import bdo_demo.exceptions.ResourceNotFoundException;
import bdo_demo.repository.TaskRepository;
import bdo_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);
        return userRepository.save(user);
    }

    @Transactional
    public Task createTaskForUser(Long userId, Task task) {
        User user = userRepository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        User user = userRepository.findByIdAndDeletedIsFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setAddress(updatedUser.getAddress());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for ID: " + userId));
        user.getTasks().forEach(task -> {
            task.setDeleted(true);
            taskRepository.save(task);
        });
        user.setDeleted(true);
        userRepository.save(user);
    }
}
