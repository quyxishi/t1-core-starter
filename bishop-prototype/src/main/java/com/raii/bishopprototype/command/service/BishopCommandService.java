package com.raii.bishopprototype.command.service;

import com.raii.bishopprototype.api.v1.model.TaskType;
import com.raii.synthetichumancorestarter.audit.aspect.WaylandWatchingYou;
import com.raii.synthetichumancorestarter.command.model.TaskPriority;
import com.raii.synthetichumancorestarter.command.model.TaskRecord;
import com.raii.synthetichumancorestarter.command.service.CommandService;
import com.raii.synthetichumancorestarter.command.service.exceptions.ExecutionQueueIsFullException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BishopCommandService {
    private final CommandService commandService;

    @WaylandWatchingYou
    public void performTask(TaskType taskType,
                            TaskPriority taskPriority,
                            String initiator) throws ExecutionQueueIsFullException {
        final var task = TaskRecord.builder()
                .description(typeToDescription(taskType))
                .priority(taskPriority)
                .author(initiator)
                .time(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
                .build();

        commandService.submitTask(task);
    }

    private String typeToDescription(TaskType taskType) {
        return switch (taskType) {
            case HELP -> "Request help information";
            case SAMPLE -> "Sample";
        };
    }
}
