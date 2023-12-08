package com.profesorp.springaop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * Log only when  the method doSomething in class com.profesorp.springaop.service.HeavyMemoryOperationService is executed
     */
    @Before("execution(* com.profesorp.springaop.service.TestService.doSomething(..))")
    public void logAll()
    {
        log.info("I'm the Aspect logAll");
    }

    @Pointcut("execution(public * com.profesorp.springaop.service.TestService.*(..))")
    public void pointCut1(){
        log.info("This, in pointCut1, is never executed.");
    }

    @Around("pointCut1()")
    public Object aroundPoint1(ProceedingJoinPoint proceedingJoinPoint) throws  Throwable
    {
        Object[] args =proceedingJoinPoint.getArgs();
        log.info("Number of Args of function: {} ",args.length);
        if (args.length==1 && "skip".equals(args[0].toString())) {
            log.info("I'm going to skip the call  of Args of function: {} ", args.length);
            return "Skip in aspect!";
        }
        Arrays.stream(args).forEach(arg -> log.info("Arg of the function: {}",arg.toString()));
        Object result=proceedingJoinPoint.proceed();
        if (result==null)
            log.info("There is not result");
        else
            log.info("Result of join is {}",result.toString());
        return result;
    }
}
