package com.raii.synthetichumancorestarter.command.executor;

import com.raii.synthetichumancorestarter.command.model.TaskRecord;
import com.raii.synthetichumancorestarter.metrics.service.MetricsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingCommandExecutor implements CommandExecutor {
    private final MetricsService metricsService;

    @Override
    @SneakyThrows
    public void execute(TaskRecord task) {
        log.info("Executing task: {}", task);

        // simulating some work...
        Thread.sleep(1000);

        metricsService.publishProceedTasksMetric(task.getAuthor());
    }
}
