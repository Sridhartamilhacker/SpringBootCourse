package com.example.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class,args);
    }
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, my first spring boot application!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue = "Friend") String name) {
        return "Hello, " + name + "! Welcome to Spring Boot!";
    }

    @GetMapping("/orders/{id}")
    public String getOrder(@PathVariable int id) {
        return "Fetching details for Order #" + id;
    }

}
