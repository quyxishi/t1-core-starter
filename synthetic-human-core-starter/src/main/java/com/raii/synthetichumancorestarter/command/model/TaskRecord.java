package com.raii.synthetichumancorestarter.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class TaskRecord {
    @NotBlank
    @Size(max = 1000)
    private final String description;

    private final TaskPriority priority;

    @NotBlank
    @Size(max = 100)
    private final String author;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}(T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?(Z|[+-]\\d{2}:\\d{2})?)?$")
    private final String time;
}
