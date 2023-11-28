package com.example.PersonalAccounting.aop;

import com.example.PersonalAccounting.util.exceptions.PaymentException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Aspect
@Slf4j
public class CrudAspect {

    @Around("CrudPointcuts.allFindMethods()")
    public Object aroundFindAdvice(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (IllegalArgumentException | NoSuchElementException e) {
            log.error("Crud service error: " + e.getMessage(), e);
            throw e;
        }catch (PaymentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            log.error("Null foreign key", e);
            throw new RuntimeException("Creating error");
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Data lookup error");
        }
    }
}
