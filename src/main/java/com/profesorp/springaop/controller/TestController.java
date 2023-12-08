package com.profesorp.springaop.controller;

import com.profesorp.springaop.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomStringUtils;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {
    final TestService service;

    @GetMapping("/memory/{numIterations}")
    public String getMemoryUsed(@PathVariable int  numIterations)
    {
        List<String> dummyData = IntStream.range(0, numIterations).boxed()
                .map(num ->  RandomStringUtils.random(50)).toList();
        log.info(STR."Creados \{numIterations} strings de 50 caracteres",numIterations);
        return service.processMillionRecords();
    }
    @GetMapping("something")
    public String doSomething()
    {
        service.doSomething();
        return "I did something";
    }
    @GetMapping("somethingMore")
    public String doSomethingMore()
    {
        service.doSomethingMore();
        return "I did something more";
    }
    @GetMapping("something/{param1}")
    public String doSomethingWithParam(@PathVariable String param1)
    {
        String var1= service.doSomethingWithParams(param1);
        return STR."I called doSomethingWithParams with param1 '\{param1}' and it returned: '\{var1}'";
    }
}
