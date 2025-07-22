package com.raii.synthetichumancorestarter;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@ConfigurationProperties(prefix = "synth.core")
public class SyntheticHumanCoreStarterProperties {
    private final int corePoolSize = 2;
    private final int maxPoolSize = 4;
    private final Duration terminationPoolTimeout = Duration.ofSeconds(60);

    private final int queueCapacity = 5;
}
