package com.jcmlabs.NexusFlow_Java_Edition.api.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflows")
public class WorkflowController {

    @PostMapping
    public String submitWorkflow() {
        return "Workflow accepted (domain only)";
    }
}
