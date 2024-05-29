package com.staxrt.tutorial.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class TransactionalCheckAspect {

    /*@Before("@within(org.springframework.stereotype.Controller) && execution(* *(..)) && @annotation(transactional)")
    public void checkTransactionalUsageInControllers(JoinPoint joinPoint, Transactional transactional) {
        System.out.println("ERROR: @Transactional annotation detected in controller method: " + joinPoint.getSignature());

        throw new IllegalStateException("Transactional annotation not allowed in controllers");
    }*/
}
