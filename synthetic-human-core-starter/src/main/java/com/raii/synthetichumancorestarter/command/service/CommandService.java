package com.raii.synthetichumancorestarter.command.service;

import com.raii.synthetichumancorestarter.command.model.TaskRecord;
import com.raii.synthetichumancorestarter.command.service.exceptions.ExecutionQueueIsFullException;
import jakarta.validation.ConstraintViolationException;

public interface CommandService {
    void submitTask(TaskRecord task) throws ExecutionQueueIsFullException, ConstraintViolationException;
}
