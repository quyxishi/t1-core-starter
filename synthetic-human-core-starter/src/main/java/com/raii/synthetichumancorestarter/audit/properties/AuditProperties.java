package com.raii.synthetichumancorestarter.audit.properties;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "synth.core.audit")
public class AuditProperties {
    @NotNull
    public AuditMode mode = AuditMode.CONSOLE;

    public String topic;

    @AssertTrue(message = "property `synth.core.audit.topic` is required and must not be empty when `synth.core.audit.mode = KAFKA`")
    public boolean isTopicValid() {
        if (mode == AuditMode.KAFKA) {
            return topic != null && !topic.isBlank();
        }
        return true;
    }

    public enum AuditMode {
        CONSOLE,
        KAFKA
    }
}
