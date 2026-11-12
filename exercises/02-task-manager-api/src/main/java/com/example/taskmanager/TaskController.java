package com.example.taskmanager;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;

    }
    @GetMapping
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Task creatTask(@RequestBody Task newTask){
        return taskRepository.save(newTask);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updated){
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updated.getTitle());
            task.setCompleted(updated.isCompleted());
            return taskRepository.save(task);
        }).orElse(null);
    }
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        taskRepository.deleteById(id);
        return "Task #" + id + " has been deleted successfully!";
    }
}
