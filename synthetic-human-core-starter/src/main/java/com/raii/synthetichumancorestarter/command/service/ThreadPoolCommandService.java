package com.raii.synthetichumancorestarter.command.service;

import com.raii.synthetichumancorestarter.SyntheticHumanCoreStarterProperties;
import com.raii.synthetichumancorestarter.command.executor.CommandExecutor;
import com.raii.synthetichumancorestarter.command.model.TaskRecord;
import com.raii.synthetichumancorestarter.command.service.exceptions.ExecutionQueueIsFullException;
import com.raii.synthetichumancorestarter.command.validation.CommandValidation;
import com.raii.synthetichumancorestarter.metrics.service.MetricsService;
import jakarta.annotation.PreDestroy;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ThreadPoolCommandService implements CommandService {
    private final SyntheticHumanCoreStarterProperties properties;

    private final CommandValidation commandValidation;
    private final CommandExecutor commandExecutor;
    private final MetricsService metricService;

    private final ThreadPoolExecutor executor;

    public ThreadPoolCommandService(SyntheticHumanCoreStarterProperties properties,
                                    CommandValidation commandValidation,
                                    CommandExecutor commandExecutor,
                                    MetricsService metricsService) {
        this.properties = properties;

        this.commandValidation = commandValidation;
        this.commandExecutor = commandExecutor;
        this.metricService = metricsService;

        this.executor = new ThreadPoolExecutor(
                properties.getCorePoolSize(),
                properties.getMaxPoolSize(),
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(properties.getQueueCapacity()),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    @Scheduled(fixedRate = 1000)
    public void publishQueueSizeMetric() {
        metricService.publishQueueSizeMetric(executor.getQueue().size());
    }

    public void submitTask(TaskRecord task) throws ExecutionQueueIsFullException, ConstraintViolationException {
        commandValidation.validate(task);

        switch (task.getPriority()) {
            case COMMON -> {
                try {
                    executor.execute(() -> commandExecutor.execute(task));
                } catch (RejectedExecutionException e) {
                    throw new ExecutionQueueIsFullException("Command [%s] rejected due exhausted execution queue".formatted(task), e);
                }
            }
            case CRITICAL -> commandExecutor.execute(task);
        }
    }

    @PreDestroy
    public void preDestroy() {
        executor.shutdown();

        try {
            if (!executor.awaitTermination(properties.getTerminationPoolTimeout().toNanos(), TimeUnit.NANOSECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
