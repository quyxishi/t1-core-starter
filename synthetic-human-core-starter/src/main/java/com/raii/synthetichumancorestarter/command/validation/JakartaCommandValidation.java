package com.raii.synthetichumancorestarter.command.validation;

import com.raii.synthetichumancorestarter.command.model.TaskRecord;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JakartaCommandValidation implements CommandValidation {
    private final Validator validator;

    @Override
    public void validate(TaskRecord taskRecord) {
        final var violations = validator.validate(taskRecord);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
