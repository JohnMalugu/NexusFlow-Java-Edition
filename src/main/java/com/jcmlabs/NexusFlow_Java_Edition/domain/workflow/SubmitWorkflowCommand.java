package com.jcmlabs.NexusFlow_Java_Edition.domain.workflow;


import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SubmitWorkflowCommand(
        @NotBlank(message = "Workflow name is required")
        String name,

        @NotEmpty(message = "At least one task must be defined")
        @Valid
        List<TaskDefinition> tasks,

        List<DependencyDefinition> dependencies
) {

    public record TaskDefinition(
            @NotBlank(message = "Task name is required")
            String name,

            @NotBlank(message = "Task type is required (e.g., SHELL, HTTP)")
            String type,

            @NotNull(message = "Payload cannot be null")
            String payload
    ) {}

    public record DependencyDefinition(
            int fromIndex,
            int toIndex
    ) {}
}
