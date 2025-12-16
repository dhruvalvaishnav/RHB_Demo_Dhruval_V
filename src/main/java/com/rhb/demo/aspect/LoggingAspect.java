package com.rhb.demo.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(* com.rhb.demo.controller..*(..))")
    public void controllerMethods() {}

    @Pointcut("execution(* com.rhb.demo.service..*(..))")
    public void serviceMethods() {}

    @Around("controllerMethods()")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        if (request != null) {
            log.info("========== REQUEST ==========");
            log.info("HTTP Method: {}", request.getMethod());
            log.info("Request URI: {}", request.getRequestURI());
            log.info("Controller: {}", joinPoint.getSignature().getDeclaringTypeName());
            log.info("Method: {}", joinPoint.getSignature().getName());
            log.info("Client IP: {}", request.getRemoteAddr());
            
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                for (Object arg : args) {
                    if (arg != null && !arg.getClass().getName().startsWith("org.springframework")) {
                        try {
                            log.info("Request Body: {}", objectMapper.writeValueAsString(arg));
                        } catch (Exception e) {
                            log.debug("Could not serialize argument");
                        }
                    }
                }
            }
        }

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            log.info("========== RESPONSE ==========");
            if (result != null) {
                try {
                    log.info("Response: {}", objectMapper.writeValueAsString(result));
                } catch (Exception e) {
                    log.info("Response Type: {}", result.getClass().getSimpleName());
                }
            }
            log.info("Execution Time: {} ms", executionTime);
            log.info("=============================");
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("========== EXCEPTION ==========");
            log.error("Method: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            log.error("Exception: {}", e.getMessage());
            log.error("Execution Time: {} ms", executionTime);
            log.error("==============================");
            throw e;
        }
    }

    @Before("serviceMethods()")
    public void logBeforeService(JoinPoint joinPoint) {
        log.debug("==> Service: {}.{}", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result) {
        log.debug("<== Service: {}.{}", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logServiceException(JoinPoint joinPoint, Exception exception) {
        log.error("Service Exception in {}.{}: {}", 
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                exception.getMessage());
    }
}

