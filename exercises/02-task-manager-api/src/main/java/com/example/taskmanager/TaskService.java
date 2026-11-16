package com.example.taskmanager;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){

        this.taskRepository = taskRepository;
        System.out.println("\uD83D\uDC49 [1. CONSTRUCTOR] TaskService is being born!");
    }

    @PostConstruct
    public void init(){
        System.out.println("\uD83D\uDC49 [2. @PostConstruct] TaskService dependencies injected! Ready for action.");
    }
    @PreDestroy
    public void cleanup(){
        System.out.println("👉 [3. @PreDestroy] Application shutting down! TaskService cleaning up.");
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
