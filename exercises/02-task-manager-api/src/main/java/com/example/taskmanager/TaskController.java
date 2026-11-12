package com.example.taskmanager;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;
    public TaskController(){
        tasks.add(new Task(nextId++, "Learn Spring Boot with Feynman", true));
        tasks.add(new Task(nextId++, "Build my first CRUD Web API", false));

    }
    @GetMapping
    public List<Task> getTasks(){
        return tasks;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id){
        for(Task task : tasks){
            if(task.getId().equals(id)){
                return task;
            }
        }
        return null;
    }

    @PostMapping
    public Task creatTask(@RequestBody Task newTask){
        newTask.setId(nextId++);
        tasks.add(newTask);
        return newTask;
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task oldTask){
        for(Task task : tasks){
            if(task.getId().equals(id)){
                task.setCompleted(oldTask.isCompleted());
                task.setTitle(oldTask.getTitle());
                return task;
            }
        }
        return null;
    }
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        tasks.removeIf(task -> task.getId().equals(id));
        return "Task #" + id + " has been deleted successfully!";
    }
}
