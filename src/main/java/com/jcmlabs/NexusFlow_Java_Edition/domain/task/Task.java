package com.jcmlabs.NexusFlow_Java_Edition.domain.task;

import com.jcmlabs.NexusFlow_Java_Edition.domain.common.TaskType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Task {

    private final UUID id;
    private final String name;
    private final TaskType type;
    private final String payload;
    private final Instant createdAt;

    private TaskState state;

    @Enumerated
    private TaskType taskType;

    private Task(UUID id, String name, TaskType type, String payload, Instant createdAt, TaskState state) {

        this.id = id;
        this.name = name;
        this.type = type;
        this.payload = payload;
        this.createdAt = createdAt;
        this.state = state;
    }

    public static Task newTask(String name, TaskType type, String payload) {
        return new Task(UUID.randomUUID(), name, type, payload, Instant.now(), TaskState.PENDING);
    }

    public static Task rehydrate(UUID id, String name, TaskType type, String payload, Instant createdAt, TaskState state) {

        return new Task(id, name, type, payload, createdAt, state);
    }

    public void schedule() {
        ensureState(TaskState.PENDING, "schedule");
        this.state = TaskState.SCHEDULED;
    }

    public void start() {
        ensureState(TaskState.SCHEDULED, "start");
        this.state = TaskState.RUNNING;
    }

    public void complete() {
        ensureState(TaskState.RUNNING, "complete");
        this.state = TaskState.SUCCESS;
    }

    public void fail() {
        ensureState(TaskState.RUNNING, "fail");
        this.state = TaskState.FAILED;
    }


    public boolean isReady() {
        return state == TaskState.PENDING;
    }

    public boolean isTerminal() {
        return state == TaskState.SUCCESS || state == TaskState.FAILED;
    }

    private void ensureState(TaskState expected, String action) {
        if (this.state != expected) {
            throw new IllegalStateException("Invalid transition: cannot " + action + " task in state " + this.state);
        }
    }
}
