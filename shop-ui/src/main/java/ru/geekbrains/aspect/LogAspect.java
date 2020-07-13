package ru.geekbrains.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* ru.geekbrains.service..*.*(..))")
    private void getName() {

    }

    @Before("getName()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("AOP logger {}", joinPoint);
    }
}
