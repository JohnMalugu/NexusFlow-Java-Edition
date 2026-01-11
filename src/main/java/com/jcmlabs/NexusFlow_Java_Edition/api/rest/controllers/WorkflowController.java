package com.jcmlabs.NexusFlow_Java_Edition.api.rest.controllers;

import com.jcmlabs.NexusFlow_Java_Edition.api.dtos.request.SubmitWorkflowRequest;
import com.jcmlabs.NexusFlow_Java_Edition.domain.workflow.SubmitWorkflowCommand;
import com.jcmlabs.NexusFlow_Java_Edition.domain.workflow.SubmitWorkflowUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/workflows")
public class WorkflowController {

    private final SubmitWorkflowUseCase useCase;

    public WorkflowController(SubmitWorkflowUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public UUID submit(@RequestBody SubmitWorkflowRequest request) {

        SubmitWorkflowCommand command =
                new SubmitWorkflowCommand(
                        request.name(),
                        request.tasks().stream()
                                .map(t -> new SubmitWorkflowCommand.TaskDefinition(
                                        t.name(), t.type(), t.payload()
                                ))
                                .toList(),
                        request.dependencies().stream()
                                .map(d -> new SubmitWorkflowCommand.DependencyDefinition(
                                        d.fromIndex(), d.toIndex()
                                ))
                                .toList()
                );

        return useCase.execute(command);
    }
}