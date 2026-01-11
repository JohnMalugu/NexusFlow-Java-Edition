package com.jcmlabs.NexusFlow_Java_Edition.infrastructure.configurations;

import com.jcmlabs.NexusFlow_Java_Edition.domain.task.TaskRepository;
import com.jcmlabs.NexusFlow_Java_Edition.domain.workflow.SubmitWorkflowUseCase;
import com.jcmlabs.NexusFlow_Java_Edition.domain.workflow.WorkflowRepository;
import com.jcmlabs.NexusFlow_Java_Edition.infrastructure.persistence.memory.InMemoryTaskRepository;
import com.jcmlabs.NexusFlow_Java_Edition.infrastructure.persistence.memory.InMemoryWorkflowRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    WorkflowRepository workflowRepository() {
        return new InMemoryWorkflowRepository();
    }

    @Bean
    TaskRepository taskRepository() {
        return new InMemoryTaskRepository();
    }

    @Bean
    SubmitWorkflowUseCase submitWorkflowUseCase(
            WorkflowRepository workflowRepository,
            TaskRepository taskRepository
    ) {
        return new SubmitWorkflowUseCase(workflowRepository, taskRepository);
    }
}
