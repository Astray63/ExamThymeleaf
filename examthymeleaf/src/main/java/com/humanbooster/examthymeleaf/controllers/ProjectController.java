package com.humanbooster.examthymeleaf.controllers;

import com.humanbooster.examthymeleaf.model.Project;
import com.humanbooster.services.ProjectService;
import com.humanbooster.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/projects")
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "projects";
    }

    @GetMapping("/projects/create")
    public String showCreateForm(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "create-project";
    }

    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            return "redirect:/projects";
        }
        model.addAttribute("project", project);
        return "project-details";
    }

    @PostMapping("/projects/create")
    public String createProject(
        @RequestParam String name,
        @RequestParam(required = false) String description,
        @RequestParam Long creatorId
    ) {
        try {
            projectService.createProject(name, creatorId);
            return "redirect:/projects";
        } catch (IllegalArgumentException e) {
            return "redirect:/projects/create?error=" + e.getMessage();
        }
    }

}
