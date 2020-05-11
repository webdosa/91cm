package com.nineone.nocm.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class AopConfig {

    @Before("execution(* com.nineone.nocm.controller.api.UserApiController(..))")
    public void testBefore(){
        log.info("test Before");
    }

}
