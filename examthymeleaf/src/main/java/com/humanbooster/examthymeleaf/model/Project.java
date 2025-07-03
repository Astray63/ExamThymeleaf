// src/main/java/com/humanbooster/examthymeleaf/model/Project.java
package com.humanbooster.examthymeleaf.model;

import java.util.List;
import java.util.ArrayList;

public class Project {
    private Long id;
    private String name;
    private User creator;
    private List<Task> tasks = new ArrayList<>();
    
    public Project() {}
    
    public Project(Long id, String name, User creator) {
        this.id = id;
        this.name = name;
        this.creator = creator;
    }
    
    // Getters et Setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    
    public User getCreator() { 
        return creator; 
    }
    
    public void setCreator(User creator) { 
        this.creator = creator; 
    }
    
    public List<Task> getTasks() { 
        return tasks; 
    }
    
    public void setTasks(List<Task> tasks) { 
        this.tasks = tasks; 
    }
    
    public int getTaskCount() { 
        return tasks.size(); 
    }
    
    public String getCreatorName() {
        return creator != null ? creator.getUsername() : "Inconnu";
    }
    
    @Override
    public String toString() {
        return "Project{id=" + id + ", name='" + name + "', creator=" + creator + "}";
    }
}
