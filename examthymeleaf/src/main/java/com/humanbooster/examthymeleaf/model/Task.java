// src/main/java/com/humanbooster/examthymeleaf/model/Task.java
package com.humanbooster.examthymeleaf.model;

public class Task {
    private Long id;
    private String title;
    private TaskStatus status;
    private User assignee;
    private Long projectId;
    
    public Task() {}
    
    public Task(Long id, String title, TaskStatus status, User assignee, Long projectId) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.assignee = assignee;
        this.projectId = projectId;
    }
    
    // Getters et Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }
    
    public TaskStatus getStatus() { 
        return status; 
    }
    
    public void setStatus(TaskStatus status) { 
        this.status = status; 
    }
    
    public User getAssignee() { 
        return assignee; 
    }
    
    public void setAssignee(User assignee) { 
        this.assignee = assignee; 
    }
    
    public Long getProjectId() { 
        return projectId; 
    }
    
    public void setProjectId(Long projectId) { 
        this.projectId = projectId; 
    }
    
    public String getAssigneeName() {
        return assignee != null ? assignee.getUsername() : "Non assigné";
    }
    
    public String getStatusDisplayName() {
        return status != null ? status.getDisplayName() : "Inconnu";
    }
    
    @Override
    public String toString() {
        return "Task{id=" + id + ", title='" + title + "', status=" + status + ", assignee=" + assignee + "}";
    }
}