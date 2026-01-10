package com.jcmlabs.NexusFlow_Java_Edition.domain.workflow;


import com.jcmlabs.NexusFlow_Java_Edition.domain.task.Task;
import com.jcmlabs.NexusFlow_Java_Edition.domain.task.TaskState;

import java.util.*;

public class Workflow {

    private final UUID id;
    private final String name;

    // DAG: task -> dependent tasks
    private final Map<Task, List<Task>> graph = new HashMap<>();

    public Workflow(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public void addTask(Task task) {
        graph.putIfAbsent(task, new ArrayList<>());
    }

    public void addDependency(Task from, Task to) {
        graph.get(from).add(to);
    }

    public List<Task> getReadyTasks() {
        List<Task> ready = new ArrayList<>();

        for (Task task : graph.keySet()) {
            boolean hasIncoming = graph.values()
                    .stream()
                    .anyMatch(deps -> deps.contains(task));

            if (!hasIncoming && task.getState() == TaskState.PENDING) {
                ready.add(task);
            }
        }
        return ready;
    }
}

