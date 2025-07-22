package com.raii.synthetichumancorestarter;

import com.raii.synthetichumancorestarter.audit.properties.AuditProperties;
import com.raii.synthetichumancorestarter.command.executor.LoggingCommandExecutor;
import com.raii.synthetichumancorestarter.command.service.ThreadPoolCommandService;
import com.raii.synthetichumancorestarter.command.validation.JakartaCommandValidation;
import com.raii.synthetichumancorestarter.metrics.service.MetricsService;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Validator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@AutoConfiguration
@EnableConfigurationProperties({SyntheticHumanCoreStarterProperties.class, AuditProperties.class})
@EnableScheduling
public class SyntheticHumanCoreStarterConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public SyntheticHumanCoreStarterExceptionHandler exceptionHandler() {
        return new SyntheticHumanCoreStarterExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public MetricsService metricsService(MeterRegistry meterRegistry) {
        return new MetricsService(meterRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public JakartaCommandValidation jakartaCommandValidation(Validator validator) {
        return new JakartaCommandValidation(validator);
    }

    @Bean
    @ConditionalOnMissingBean
    public LoggingCommandExecutor loggingCommandExecutor(MetricsService metricsService) {
        return new LoggingCommandExecutor(metricsService);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadPoolCommandService threadPoolCommandService(SyntheticHumanCoreStarterProperties properties,
                                                             JakartaCommandValidation commandValidation,
                                                             LoggingCommandExecutor commandExecutor,
                                                             MetricsService metricsService) {
        return new ThreadPoolCommandService(properties, commandValidation, commandExecutor, metricsService);
    }
}
