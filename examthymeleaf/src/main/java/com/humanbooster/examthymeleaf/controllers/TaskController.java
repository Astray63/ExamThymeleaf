package com.humanbooster.examthymeleaf.controllers;

import com.humanbooster.examthymeleaf.model.Project;
import com.humanbooster.examthymeleaf.model.Task;
import com.humanbooster.examthymeleaf.model.TaskStatus;
import com.humanbooster.examthymeleaf.model.User;
import com.humanbooster.services.ProjectService;
import com.humanbooster.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {
    private final ProjectService projectService;
    private final UserService userService;

    public TaskController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/tasks/create")
    public String showCreateTaskForm(@RequestParam(required = false) Long projectId, Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("projectId", projectId);
        return "create-task";
    }

    @PostMapping("/tasks/create")
    public String createTask(
            @RequestParam String name,
            @RequestParam(required = false) String description, // non utilisé dans Task mais présent dans le formulaire
            @RequestParam TaskStatus status,
            @RequestParam Long projectId,
            @RequestParam(required = false) Long assigneeId
    ) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return "redirect:/projects?error=Projet introuvable";
        }
        User assignee = assigneeId != null ? userService.getUserById(assigneeId) : null;
        Task task = new Task();
        task.setTitle(name);
        task.setStatus(status);
        task.setAssignee(assignee);
        projectService.addTaskToProject(projectId, task);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/tasks/{id}/update-status")
    public String updateTaskStatus(
            @PathVariable Long id,
            @RequestParam Long projectId
    ) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return "redirect:/projects?error=Projet introuvable";
        }
        Task task = project.getTasks().stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
        if (task == null) {
            return "redirect:/projects/" + projectId + "?error=Tâche introuvable";
        }
        // Passer au statut suivant
        TaskStatus[] statuses = TaskStatus.values();
        int nextIndex = (task.getStatus().ordinal() + 1) % statuses.length;
        projectService.updateTaskStatus(projectId, id, statuses[nextIndex]);
        return "redirect:/projects/" + projectId;
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(
            @PathVariable Long id,
            @RequestParam Long projectId
    ) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return "redirect:/projects?error=Projet introuvable";
        }
        project.getTasks().removeIf(t -> t.getId().equals(id));
        return "redirect:/projects/" + projectId;
    }
} 