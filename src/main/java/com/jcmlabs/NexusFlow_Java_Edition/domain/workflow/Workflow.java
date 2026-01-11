package com.jcmlabs.NexusFlow_Java_Edition.domain.workflow;


import com.jcmlabs.NexusFlow_Java_Edition.domain.task.Task;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE) // Forces use of factory methods
public class Workflow {

    private final UUID id;
    private final String name;

    // The graph represents the Directed Acyclic Graph (DAG) logic
    private final Map<Task, List<Task>> graph = new HashMap<>();

    /**
     * Factory method to create a new Workflow.
     * Logic for generating IDs stays in the domain.
     */
    public static Workflow create(String name) {
        return new Workflow(UUID.randomUUID(), name);
    }

    public void addTask(Task task) {
        graph.putIfAbsent(task, new ArrayList<>());
    }

    public void addDependency(Task from, Task to) {
        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            throw new IllegalArgumentException("Both tasks must be added to the workflow first");
        }
        graph.get(from).add(to);
    }

    public List<Task> getReadyTasks() {
        return graph.keySet().stream()
                .filter(task -> !hasIncomingDependencies(task))
                .filter(Task::isPending)
                .toList();
    }

    private boolean hasIncomingDependencies(Task task) {
        return graph.values().stream()
                .anyMatch(deps -> deps.contains(task));
    }
}