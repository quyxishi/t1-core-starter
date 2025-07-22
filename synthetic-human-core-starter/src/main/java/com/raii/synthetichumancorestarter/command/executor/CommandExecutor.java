package com.raii.synthetichumancorestarter.command.executor;

import com.raii.synthetichumancorestarter.command.model.TaskRecord;

public interface CommandExecutor {
    void execute(TaskRecord task);
}
