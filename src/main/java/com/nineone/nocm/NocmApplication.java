package com.nineone.nocm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@ServletComponentScan
@SpringBootApplication
public class NocmApplication {

    public static void main(String[] args) {
        SpringApplication.run(NocmApplication.class, args);  
    } 

}
