package com.raii.synthetichumancorestarter.command.service.exceptions;

public class ExecutionQueueIsFullException extends Exception {
    public ExecutionQueueIsFullException(String message, Exception e) {
        super(message, e);
    }
}
