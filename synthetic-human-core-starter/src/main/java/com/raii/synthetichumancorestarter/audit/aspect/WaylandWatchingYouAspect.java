package com.raii.synthetichumancorestarter.audit.aspect;

import com.raii.synthetichumancorestarter.audit.model.AuditEvent;
import com.raii.synthetichumancorestarter.audit.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class WaylandWatchingYouAspect {
    private final AuditService auditService;

    @Around("@annotation(com.raii.synthetichumancorestarter.audit.aspect.WaylandWatchingYou)")
    public Object auditMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        final var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final var auditEventBuilder = AuditEvent.builder()
                .method(method.getName())
                .params(extractMethodParameters(method, joinPoint.getArgs()))
                .time(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));

        try {
            final var result = joinPoint.proceed();
            auditEventBuilder.state(AuditEvent.MethodExecutionState.SUCCESS);
            return result;
        } catch (Throwable e) {
            auditEventBuilder.state(AuditEvent.MethodExecutionState.EXCEPTION);
            throw e;
        } finally {
            auditService.audit(auditEventBuilder.build());
        }
    }

    public Map<String, AuditEvent.Param> extractMethodParameters(Method method, Object[] args) {
        final var params = new HashMap<String, AuditEvent.Param>();
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            params.put(param.getName(), new AuditEvent.Param(param.getType().getSimpleName(), args[i]));
        }

        return params;
    }
}
