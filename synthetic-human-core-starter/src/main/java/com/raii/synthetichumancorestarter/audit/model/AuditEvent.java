package com.raii.synthetichumancorestarter.audit.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AuditEvent {
    private String method;
    private Map<String, Param> params;
    private MethodExecutionState state;
    private String time;

    public record Param(String type, Object value) {
    }

    public enum MethodExecutionState {
        SUCCESS,
        EXCEPTION,
    }
}
