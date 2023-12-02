package com.profesorp.springaop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HeavyMemoryOperationService{


    @CheckMemory(jvmMemoryUnit = CheckMemory.MemoryUnit.MB, minRequiredMemory = 512)
    public void processMillionRecords() {
        log.info("in processMillionRecords");
    }
}
