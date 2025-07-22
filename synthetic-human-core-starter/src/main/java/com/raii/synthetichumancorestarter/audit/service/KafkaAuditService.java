package com.raii.synthetichumancorestarter.audit.service;

import com.raii.synthetichumancorestarter.audit.model.AuditEvent;
import com.raii.synthetichumancorestarter.audit.properties.AuditProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "synth.core.audit.mode", havingValue = "KAFKA")
public class KafkaAuditService implements AuditService {
    private final AuditProperties properties;
    private final KafkaTemplate<String, AuditEvent> kafkaTemplate;

    @Override
    public void audit(AuditEvent event) {
        kafkaTemplate.send(properties.getTopic(), event);
    }
}
