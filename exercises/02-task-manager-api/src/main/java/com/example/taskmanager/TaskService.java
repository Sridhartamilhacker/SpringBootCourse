package com.example.taskmanager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public void createTaskWithoutTransaction(){
        taskRepository.save(new Task("Unprotected Task", false));
        throw new RuntimeException("💥 Boom! Sudden server crash!");
    }

    @Transactional
    public void createTaskWithTransaction(){
        taskRepository.save(new Task("Protected Task", false));
        throw new RuntimeException("💥 Boom! Sudden server crash!");
    }
}
