package com.mastery.ioc.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ContainerInspectorService {

    private final ApplicationContext context;

    // ApplicationContext is automatically injectable by Spring!
    public ContainerInspectorService(ApplicationContext context) {
        this.context = context;
    }

    public int getTotalBeanCount() {
        return context.getBeanDefinitionCount();
    }

    public List<String> getUserDefinedBeans() {
        return Arrays.stream(context.getBeanDefinitionNames())
                .filter(name -> !name.startsWith("org.springframework"))
                .sorted()
                .toList();
    }

    public boolean isSingleton(String beanName) {
        return context.isSingleton(beanName);
    }
}
