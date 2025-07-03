// src/main/java/com/humanbooster/controllers/MainController.java
package com.humanbooster.controllers;

import com.humanbooster.examthymeleaf.model.Project;
import com.humanbooster.examthymeleaf.model.Task;
import com.humanbooster.examthymeleaf.model.TaskStatus;
import com.humanbooster.examthymeleaf.model.User;
import com.humanbooster.services.ProjectService;
import com.humanbooster.services.TaskService;
import com.humanbooster.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MainController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private TaskService taskService;
    
    // Page d'accueil
    @GetMapping("/")
    public String home(Model model) {
        List<User> users = userService.getAllUsers();
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("users", users != null ? users : new java.util.ArrayList<>());
        model.addAttribute("projects", projects != null ? projects : new java.util.ArrayList<>());
        return "index";
    }
    
    // Liste des utilisateurs
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
    
    // Liste des projets
    @GetMapping("/projects")
    public String projects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects";
    }
    
    // Détails d'un projet
    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return "redirect:/projects";
        }
        
        model.addAttribute("project", project);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("taskStatuses", TaskStatus.values());
        return "project-details";
    }
    
    // Formulaire de création de projet
    @GetMapping("/projects/create")
    public String createProjectForm(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "create-project";
    }
    
    // Création d'un projet
    @PostMapping("/projects/create")
    public String createProject(@RequestParam String name, 
                               @RequestParam Long creatorId,
                               RedirectAttributes redirectAttributes) {
        try {
            Project project = projectService.createProject(name, creatorId);
            redirectAttributes.addFlashAttribute("success", "Projet créé avec succès !");
            return "redirect:/projects/" + project.getId();
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/projects/create";
        }
    }
    
    // Formulaire de création de tâche
    @GetMapping("/tasks/create")
    public String createTaskForm(@RequestParam Long projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return "redirect:/projects";
        }
        
        model.addAttribute("project", project);
        model.addAttribute("users", userService.getAllUsers());
        return "create-task";
    }
    
    // Création d'une tâche
    @PostMapping("/tasks/create")
    public String createTask(@RequestParam String title,
                            @RequestParam Long projectId,
                            @RequestParam(required = false) Long assigneeId,
                            RedirectAttributes redirectAttributes) {
        try {
            taskService.createTask(title, projectId, assigneeId);
            redirectAttributes.addFlashAttribute("success", "Tâche créée avec succès !");
            return "redirect:/projects/" + projectId;
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/tasks/create?projectId=" + projectId;
        }
    }
    
    // Changer le statut d'une tâche
    @PostMapping("/tasks/{id}/next-status")
    public String nextTaskStatus(@PathVariable Long id, 
                                @RequestParam Long projectId,
                                RedirectAttributes redirectAttributes) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            TaskStatus nextStatus = taskService.getNextStatus(task.getStatus());
            taskService.updateTaskStatus(id, nextStatus);
            redirectAttributes.addFlashAttribute("success", "Statut de la tâche mis à jour !");
        }
        return "redirect:/projects/" + projectId;
    }
    
    // Supprimer une tâche
    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id, 
                            @RequestParam Long projectId,
                            RedirectAttributes redirectAttributes) {
        taskService.deleteTask(id);
        redirectAttributes.addFlashAttribute("success", "Tâche supprimée avec succès !");
        return "redirect:/projects/" + projectId;
    }
    
    // Supprimer un projet
    @PostMapping("/projects/{id}/delete")
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        projectService.deleteProject(id);
        redirectAttributes.addFlashAttribute("success", "Projet supprimé avec succès !");
        return "redirect:/projects";
    }
}