package com.profesorp.springaop.aop;


import com.profesorp.springaop.exception.LowMemoryException;
import lombok.Getter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckMemoryAspect {

    static final Logger logger = LoggerFactory.getLogger(CheckMemoryAspect.class);
    @Getter
    static String lastMsg;

    @Before("@annotation(checkMemory)")
    public void executionTimeLogger(JoinPoint joinPoint, CheckMemory checkMemory) {
        long maxAvailableMemory = getMaxAvailableMemory(checkMemory.jvmMemoryUnit());
        long minRequiredMemory = checkMemory.minRequiredMemory();
        if (minRequiredMemory != 0 && minRequiredMemory > maxAvailableMemory) {
            logger.warn("Checking JVM memory before calling method: {}", joinPoint.getSignature());
            lastMsg=STR."Max free memory in JVM is \{maxAvailableMemory} \{checkMemory.jvmMemoryUnit()} and it is lower than defined threshold memory: \{minRequiredMemory}\{checkMemory.jvmMemoryUnit()} ";
            logger.warn(lastMsg);
            throw new LowMemoryException("JVM Memory is Low hence suspending operation");
        }
    }

    private long getMaxAvailableMemory(CheckMemory.MemoryUnit memoryUnit) {
        long maxFreeMemory = getMemory(Runtime.getRuntime().maxMemory(), memoryUnit);
        long usedMemory = getMemory(
                Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), memoryUnit);
        lastMsg=STR."Max JVM Memory: \{maxFreeMemory} and Current Used JVM Memory: \{usedMemory}";
        logger.info(lastMsg);
        return (maxFreeMemory - usedMemory);
    }

    private long getMemory(long memorySize, CheckMemory.MemoryUnit memoryUnit) {
        int byteToMb = 1024 * 1024;
        int byteToKb = 1024;

        switch (memoryUnit) {
            case KB:
                return memorySize / byteToKb;
            case MB:
                return memorySize / byteToMb;
            default:
                return memorySize;
        }
    }
}
