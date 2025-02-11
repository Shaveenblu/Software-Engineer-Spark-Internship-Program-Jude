package com.jude.sparkinternshipjude.service;

import com.jude.sparkinternshipjude.model.Task;
import com.jude.sparkinternshipjude.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Task markTaskAsCompleted(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setCompleted(true);
            return taskRepository.save(task);
        }
        return null;
    }
    
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
    
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }
}
