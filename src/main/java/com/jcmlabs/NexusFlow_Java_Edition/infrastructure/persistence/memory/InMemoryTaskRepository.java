package com.jcmlabs.NexusFlow_Java_Edition.infrastructure.persistence.memory;

import com.jcmlabs.NexusFlow_Java_Edition.domain.task.Task;
import com.jcmlabs.NexusFlow_Java_Edition.domain.task.TaskRepository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTaskRepository implements TaskRepository {

    private final Map<UUID, Task> store = new ConcurrentHashMap<>();

    @Override
    public void saveAll(Collection<Task> tasks) {
        tasks.forEach(task -> store.put(task.getId(), task));
    }
}
