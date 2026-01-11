package com.jcmlabs.NexusFlow_Java_Edition.domain.task;

import java.util.Collection;

public interface TaskRepository {
    void saveAll(Collection<Task> tasks);
}