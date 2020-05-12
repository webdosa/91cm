package com.nineone.nocm.config;

import com.nineone.nocm.domain.User;
import com.nineone.nocm.repository.UserAuthoritiesRepository;
import com.nineone.nocm.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Slf4j
@Aspect
@Component
public class AopConfig {

    @Autowired
    private UserAuthoritiesRepository authoritiesRepository;
    @Autowired
    private HttpSession httpSession;

    @Pointcut("execution(* com.nineone.nocm.controller.api.UserApiController.getSessionUser(..))")
    public void getSessionUser(){}

    @Pointcut("execution(* com.nineone.nocm.controller.api.UserApiController.userInfo(..))")
    public void userInfo(){}

//    @Before("execution(* com.nineone.nocm.controller.api.UserApiController.userInfo(*)) && args(user)")
//    public void addUserRoles(User user){
//        log.info(authoritiesRepository.getUserRoles(user.getEmail()).get(0));
//        user.setRoles(authoritiesRepository.getUserRoles(user.getEmail()));
//    }

//    @Around("execution(* com.nineone.nocm.controller.api.*.*(..))")
//    public Object api(ProceedingJoinPoint joinPoint) throws Throwable{
//        User user = (User)httpSession.getAttribute("user");
//        log.info(user.getName());
//        if (user.getName().equals("원주민")){
//            log.info("test api");
//            Object result =joinPoint.proceed();
//            return result;
//        }
//        return null;
//    }

    @Around("userInfo()||getSessionUser()")
    public Object CheckUserRole(ProceedingJoinPoint joinPoint) throws Throwable{
        User user = (User)joinPoint.getArgs()[0];
        user.setRoles(authoritiesRepository.getUserRoles(user.getEmail()));
        for (String role : user.getRoles()){
            if (role.equals("ROLE_ADMIN")){
                return joinPoint.proceed();
            }
            if (role.equals("ROLE_USER")){
                return joinPoint.proceed();
            }
        }
        if (user.getPhone()== null){
            return joinPoint.proceed();
        }
        return new ResponseEntity<>("{}",HttpStatus.BAD_REQUEST);

//        if (user.getName().equals("원주민")){
//            return joinPoint.proceed();
//        }else{
//            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
//        }
    }
}
