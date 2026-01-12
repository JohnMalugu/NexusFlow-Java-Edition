package com.jcmlabs.NexusFlow_Java_Edition.api.dtos.request;

import java.util.List;

public record SubmitWorkflowRequest(
        String name,
        List<TaskRequest> tasks,
        List<DependencyRequest> dependencies
) {
    public record TaskRequest(
            String name,
            String type,
            String payload
    ) {}

    public record DependencyRequest(
            int fromIndex,
            int toIndex
    ) {}
}
