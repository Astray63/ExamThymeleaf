// src/main/java/com/humanbooster/services/TaskService.java
package com.humanbooster.services;

import com.humanbooster.examthymeleaf.model.Task;
import com.humanbooster.examthymeleaf.model.TaskStatus;
import com.humanbooster.examthymeleaf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private UserService userService;
    
    private Map<Long, Task> tasks = new HashMap<>();
    private Long nextId = 1L;
    
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    
    public Task getTaskById(Long id) {
        return tasks.get(id);
    }
    
    public List<Task> getTasksByProjectId(Long projectId) {
        return tasks.values().stream()
                .filter(task -> Objects.equals(task.getProjectId(), projectId))
                .collect(Collectors.toList());
    }
    
    public Task createTask(String title, Long projectId, Long assigneeId) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre de la tâche ne peut pas être vide");
        }
        
        User assignee = null;
        if (assigneeId != null) {
            assignee = userService.getUserById(assigneeId);
            if (assignee == null) {
                throw new IllegalArgumentException("Utilisateur assigné non trouvé");
            }
        }
        
        Task task = new Task(nextId++, title.trim(), TaskStatus.TODO, assignee, projectId);
        tasks.put(task.getId(), task);
        return task;
    }
    
    public Task updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setStatus(newStatus);
        }
        return task;
    }
    
    public Task updateTaskAssignee(Long taskId, Long assigneeId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            User assignee = assigneeId != null ? userService.getUserById(assigneeId) : null;
            task.setAssignee(assignee);
        }
        return task;
    }
    
    public void deleteTask(Long id) {
        tasks.remove(id);
    }
    
    public void deleteTasksByProjectId(Long projectId) {
        tasks.entrySet().removeIf(entry -> 
            Objects.equals(entry.getValue().getProjectId(), projectId));
    }
    
    public TaskStatus getNextStatus(TaskStatus currentStatus) {
        switch (currentStatus) {
            case TODO:
                return TaskStatus.IN_PROGRESS;
            case IN_PROGRESS:
                return TaskStatus.DONE;
            case DONE:
                return TaskStatus.TODO;
            default:
                return TaskStatus.TODO;
        }
    }
}