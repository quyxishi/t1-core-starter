package com.raii.synthetichumancorestarter.audit.service;

import com.raii.synthetichumancorestarter.audit.model.AuditEvent;

public interface AuditService {
    void audit(AuditEvent event);
}
