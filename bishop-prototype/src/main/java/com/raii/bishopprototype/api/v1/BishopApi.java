package com.raii.bishopprototype.api.v1;

import com.raii.bishopprototype.api.v1.model.TaskType;
import com.raii.bishopprototype.command.service.BishopCommandService;
import com.raii.synthetichumancorestarter.command.model.TaskPriority;
import com.raii.synthetichumancorestarter.command.service.exceptions.ExecutionQueueIsFullException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class BishopApi {
    private final BishopCommandService commandService;

    @PostMapping
    public void processTask(
            @RequestParam TaskType taskType,
            @RequestParam TaskPriority taskPriority,
            @RequestParam String initiator) throws ExecutionQueueIsFullException {
        commandService.performTask(taskType, taskPriority, initiator);
    }
}
