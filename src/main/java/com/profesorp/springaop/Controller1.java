package com.profesorp.springaop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomStringUtils;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller1 {
    final HeavyMemoryOperationService service;

    @GetMapping("/{numero}")
    public void hello(@PathVariable int  numero)
    {
        List<String> dummyData = IntStream.range(0, numero).boxed()
                .map(num ->  RandomStringUtils.random(50)).toList();
        log.info(STR."Creados \{numero} strings de 50 caracteres",numero);
        service.processMillionRecords();
    }
}
