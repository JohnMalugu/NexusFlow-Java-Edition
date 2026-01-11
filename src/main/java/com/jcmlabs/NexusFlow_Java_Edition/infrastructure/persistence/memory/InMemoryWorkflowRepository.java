package com.jcmlabs.NexusFlow_Java_Edition.infrastructure.persistence.memory;

import com.jcmlabs.NexusFlow_Java_Edition.domain.workflow.Workflow;
import com.jcmlabs.NexusFlow_Java_Edition.domain.workflow.WorkflowRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryWorkflowRepository implements WorkflowRepository {

    private final Map<UUID, Workflow> store = new ConcurrentHashMap<>();

    @Override
    public void save(Workflow workflow) {
        store.put(workflow.getId(), workflow);
    }
}
