package com.jcmlabs.NexusFlow_Java_Edition.domain.task;

import java.time.Instant;
import java.util.UUID;

public class Task {

    private final UUID id;
    private final String name;
    private TaskState state;
    private final Instant createdAt;

    public Task(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.state = TaskState.PENDING;
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public TaskState getState() { return state; }

    public void schedule() {
        if (state != TaskState.PENDING) {
            throw new IllegalStateException("Task must be PENDING to schedule");
        }
        this.state = TaskState.SCHEDULED;
    }

    public void start() {
        if (state != TaskState.SCHEDULED) {
            throw new IllegalStateException("Task must be SCHEDULED to start");
        }
        this.state = TaskState.RUNNING;
    }

    public void complete() {
        if (state != TaskState.RUNNING) {
            throw new IllegalStateException("Task must be RUNNING to complete");
        }
        this.state = TaskState.SUCCESS;
    }

    public void fail() {
        if (state != TaskState.RUNNING) {
            throw new IllegalStateException("Task must be RUNNING to fail");
        }
        this.state = TaskState.FAILED;
    }
}
