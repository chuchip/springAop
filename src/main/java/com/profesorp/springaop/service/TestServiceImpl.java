package com.profesorp.springaop.service;

import com.profesorp.springaop.aop.CheckMemory;
import com.profesorp.springaop.aop.CheckMemoryAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestServiceImpl implements  TestService{

    @CheckMemory(jvmMemoryUnit = CheckMemory.MemoryUnit.MB, minRequiredMemory = 512)
    @Override
    public String processMillionRecords() {
        log.info("in processMillionRecords");
        return CheckMemoryAspect.getLastMsg();
    }
    @Override
    public void doSomething()
    {
        log.info("Here, I'll do something");
    }
    @Override
    public void doSomethingMore()
    {
        log.info("Here, I'll do something More");
    }
    @Override
    public  String doSomethingWithParams(String param1)
    {
        log.info("I've received the param `{}` ",param1);
        return "\nI've received the param '"+param1+"'";
    }
}
