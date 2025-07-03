// src/main/java/com/humanbooster/services/ProjectService.java
package com.humanbooster.services;

import com.humanbooster.examthymeleaf.model.Project;
import com.humanbooster.examthymeleaf.model.Task;
import com.humanbooster.examthymeleaf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProjectService {
    @Autowired
    private UserService userService;
    
    @Autowired
    private TaskService taskService;
    
    private Map<Long, Project> projects = new HashMap<>();
    private Long nextId = 1L;
    
    public ProjectService() {}
    
    public List<Project> getAllProjects() {
        List<Project> allProjects = new ArrayList<>(projects.values());
        // Mettre à jour les tâches pour chaque projet
        for (Project project : allProjects) {
            updateProjectTasks(project.getId());
        }
        return allProjects;
    }
    
    public Project getProjectById(Long id) {
        Project project = projects.get(id);
        if (project != null) {
            updateProjectTasks(id);
        }
        return project;
    }
    
    public Project createProject(String name, Long creatorId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom du projet ne peut pas être vide");
        }
        
        User creator = userService.getUserById(creatorId);
        if (creator == null) {
            throw new IllegalArgumentException("Créateur non trouvé");
        }
        
        Project project = new Project(nextId++, name.trim(), creator);
        projects.put(project.getId(), project);
        return project;
    }
    
    public void updateProjectTasks(Long projectId) {
        Project project = projects.get(projectId);
        if (project != null) {
            List<Task> projectTasks = taskService.getTasksByProjectId(projectId);
            project.setTasks(projectTasks);
        }
    }
    
    public void deleteProject(Long id) {
        // Supprimer d'abord toutes les tâches associées
        taskService.deleteTasksByProjectId(id);
        // Puis supprimer le projet
        projects.remove(id);
    }
    
    public Project updateProject(Long id, String name) {
        Project project = projects.get(id);
        if (project != null && name != null && !name.trim().isEmpty()) {
            project.setName(name.trim());
        }
        return project;
    }
    
    public boolean existsById(Long id) {
        return projects.containsKey(id);
    }
}