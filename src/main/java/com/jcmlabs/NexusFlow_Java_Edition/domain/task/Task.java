package com.jcmlabs.NexusFlow_Java_Edition.domain.task;

import com.jcmlabs.NexusFlow_Java_Edition.domain.common.TaskType;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Task {

    @EqualsAndHashCode.Include
    private final UUID id;
    private final String name;
    private final TaskType type;
    private final String payload;
    private final Instant createdAt;

    private TaskState state;

    /**
     * Factory for brand new tasks.
     * Handles ID and Timestamp generation internally.
     */
    public static Task newTask(String name, TaskType type, String payload) {
        return new Task(UUID.randomUUID(), name, type, payload, Instant.now(), TaskState.PENDING);
    }

    /**
     * Factory for recreating tasks from persistence (DB/Cache).
     */
    public static Task rehydrate(UUID id, String name, TaskType type, String payload, Instant createdAt, TaskState state) {
        return new Task(id, name, type, payload, createdAt, state);
    }

    // --- State Transitions ---

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

    // --- Helpers ---

    public boolean isReady() {
        return state == TaskState.PENDING;
    }

    public boolean isTerminal() {
        return state == TaskState.SUCCESS || state == TaskState.FAILED;
    }

    private void ensureState(TaskState expected, String action) {
        if (this.state != expected) {
            throw new IllegalStateException(
                    String.format("Invalid transition: cannot %s task in state %s", action, this.state)
            );
        }
    }
}
