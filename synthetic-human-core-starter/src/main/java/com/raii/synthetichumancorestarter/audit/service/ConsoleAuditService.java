package com.raii.synthetichumancorestarter.audit.service;

import com.raii.synthetichumancorestarter.audit.model.AuditEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(name = "synth.core.audit.mode", havingValue = "CONSOLE", matchIfMissing = true)
public class ConsoleAuditService implements AuditService {
    @Override
    public void audit(AuditEvent event) {
        log.info("Auditing event: {}", event);
    }
}
