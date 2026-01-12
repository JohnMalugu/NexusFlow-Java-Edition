package com.jcmlabs.NexusFlow_Java_Edition.domain.workflow;


import com.jcmlabs.NexusFlow_Java_Edition.domain.task.Task;
import com.jcmlabs.NexusFlow_Java_Edition.domain.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SubmitWorkflowUseCase {

    private final WorkflowRepository workflowRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public UUID execute(SubmitWorkflowCommand command) {

        Workflow workflow = Workflow.create(command.name());

        List<Task> domainTasks = command.tasks().stream()
                .map(def -> Task.newTask(
                        def.name(),
                        Task.TaskType.valueOf(def.type().toUpperCase()),
                        def.payload()
                ))
                .toList();

        domainTasks.forEach(workflow::addTask);

        if (command.dependencies() != null) {
            for (var dep : command.dependencies()) {
                validateIndices(dep, domainTasks.size());
                Task from = domainTasks.get(dep.fromIndex());
                Task to = domainTasks.get(dep.toIndex());
                workflow.addDependency(from, to);
            }
        }

        workflow.getReadyTasks().forEach(Task::schedule);

        workflowRepository.save(workflow);
        taskRepository.saveAll(domainTasks);

        return workflow.getId();
    }

    private void validateIndices(SubmitWorkflowCommand.DependencyDefinition dep, int size) {
        if (dep.fromIndex() >= size || dep.toIndex() >= size || dep.fromIndex() < 0 || dep.toIndex() < 0) {
            throw new IllegalArgumentException("Dependency index out of bounds");
        }
    }
}