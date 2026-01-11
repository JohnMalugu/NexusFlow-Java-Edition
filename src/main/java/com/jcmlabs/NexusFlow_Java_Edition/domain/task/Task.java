package com.jcmlabs.NexusFlow_Java_Edition.domain.task;

import lombok.Getter;
import java.time.Instant;
import java.util.UUID;

@Getter // Automatically generates getId(), getName(), getState(), etc.
public class Task {

    private final UUID id;
    private final String name;
    private TaskState state;
    private final Instant createdAt;

    // Standard constructor for new tasks
    public Task(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.state = TaskState.PENDING;
        this.createdAt = Instant.now();
    }

    // Reconstitution constructor (Used by the Mapper when loading from DB)
    public Task(UUID id, String name, TaskState state, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.createdAt = createdAt;
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

    public boolean isPending() {
        return state == TaskState.PENDING;
    }

    // Private helper to keep the business methods clean
    private void ensureState(TaskState expected, String action) {
        if (this.state != expected) {
            throw new IllegalStateException(
                    String.format("Invalid transition: Task must be %s to %s, but was %s",
                            expected, action, this.state)
            );
        }
    }
}