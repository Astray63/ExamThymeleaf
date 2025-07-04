
package com.humanbooster.examthymeleaf.model;

public class Task {
    private Long id;
    private String title;
    private TaskStatus status;
    private User assignee;
    
    public Task() {}
    
    public Task(Long id, String title, TaskStatus status, User assignee) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.assignee = assignee;
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
