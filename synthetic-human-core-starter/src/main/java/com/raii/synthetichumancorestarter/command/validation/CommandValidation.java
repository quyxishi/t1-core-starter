package com.raii.synthetichumancorestarter.command.validation;

import com.raii.synthetichumancorestarter.command.model.TaskRecord;
import jakarta.validation.ConstraintViolationException;

public interface CommandValidation {
    void validate(TaskRecord task) throws ConstraintViolationException;
}
