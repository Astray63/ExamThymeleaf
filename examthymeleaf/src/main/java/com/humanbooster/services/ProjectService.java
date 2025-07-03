package com.humanbooster.services;

import com.humanbooster.examthymeleaf.model.Project;
import com.humanbooster.examthymeleaf.model.Task;
import com.humanbooster.examthymeleaf.model.TaskStatus;
import com.humanbooster.examthymeleaf.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final List<Project> projects = new ArrayList<>();
    private final UserService userService;

    public ProjectService(UserService userService) {
        this.userService = userService;
        initializeSampleProjects();
    }

    private void initializeSampleProjects() {
        User admin = userService.getUserById(1L);
        projects.add(new Project(1L, "Site Web Corporate", admin));
        projects.add(new Project(2L, "Application Mobile", admin));
    }

    public List<Project> getAllProjects() {
        return projects;
    }

    public void createProject(String name, Long creatorId) {
        User creator = userService.getUserById(creatorId);
        if (creator == null) {
            throw new IllegalArgumentException("Utilisateur créateur introuvable");
        }
        Project project = new Project((long) (projects.size() + 1), name, creator);
        projects.add(project);
    }

    public Project getProjectById(Long id) {
        return projects.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void addTaskToProject(Long projectId, Task task) {
        Project project = getProjectById(projectId);
        if (project != null) {
            task.setId((long) (project.getTasks().size() + 1));
            project.getTasks().add(task);
        }
    }

    public void updateTaskStatus(Long projectId, Long taskId, TaskStatus newStatus) {
        Project project = getProjectById(projectId);
        if (project != null) {
            project.getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .ifPresent(task -> task.setStatus(newStatus));
        }
    }
}
