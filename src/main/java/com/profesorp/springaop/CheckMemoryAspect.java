package com.profesorp.springaop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.profesorp.springaop.CheckMemory.MemoryUnit.KB;
import static com.profesorp.springaop.CheckMemory.MemoryUnit.MB;

@Aspect
@Component
public class CheckMemoryAspect {

    static final Logger logger = LoggerFactory.getLogger(CheckMemoryAspect.class);

    @Before("@annotation(checkMemory)")
    public void executionTimeLogger(JoinPoint joinPoint, CheckMemory checkMemory) {
        long maxAvailableMemory = getMaxAvailableMemory(checkMemory.jvmMemoryUnit());
        long minRequiredMemory = checkMemory.minRequiredMemory();
        if (minRequiredMemory != 0 && minRequiredMemory > maxAvailableMemory) {
            logger.warn("Checking JVM memory before calling method: {}", joinPoint.getSignature());
            logger.warn(
                    "Max free memory in JVM is {}{} and it is lower than defined threshold memory: {}{} ",
                    maxAvailableMemory, checkMemory.jvmMemoryUnit(), minRequiredMemory,
                    checkMemory.jvmMemoryUnit());
            // Raise Email Alert
            // Raise Exception
            throw new LowMemoryException("JVM Memory is Low hence suspending operation");
        }
    }

    private long getMaxAvailableMemory(CheckMemory.MemoryUnit memoryUnit) {
        long maxFreeMemory = getMemory(Runtime.getRuntime().maxMemory(), memoryUnit);
        long usedMemory = getMemory(
                Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), memoryUnit);
        logger.info("Max JVM Memory: {} and Current Used JVM Memory: {}", maxFreeMemory, usedMemory);
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
