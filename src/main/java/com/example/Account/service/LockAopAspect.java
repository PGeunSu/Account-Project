package com.example.Account.service;

import com.example.Account.aop.AccountLockIdInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LockAopAspect {
    private  final LockService lockService;

    @Around("@annotation(com.example.Account.aop.AccountLock) && args(request)")
    public Object aroundMethod(
            ProceedingJoinPoint pjp,
            AccountLockIdInterface request

    ) throws Throwable {
        lockService.lock(request.getAccountNumber());
         try{
             return pjp.proceed();
             //after
         }finally {
             //lock 해제
            lockService.unlock(request.getAccountNumber());
         }
    }
}
