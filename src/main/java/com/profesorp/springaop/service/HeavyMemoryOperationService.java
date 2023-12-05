package com.profesorp.springaop.service;

import com.profesorp.springaop.aop.CheckMemory;
import com.profesorp.springaop.aop.CheckMemoryAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HeavyMemoryOperationService{


    @CheckMemory(jvmMemoryUnit = CheckMemory.MemoryUnit.MB, minRequiredMemory = 512)
    public String processMillionRecords() {
        log.info("in processMillionRecords");
        return CheckMemoryAspect.getLastMsg();
    }
}
