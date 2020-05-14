package com.nineone.nocm.config;

import com.nineone.nocm.domain.Channel;
import com.nineone.nocm.domain.Invite;
import com.nineone.nocm.domain.User;
import com.nineone.nocm.repository.ChannelRepository;
import com.nineone.nocm.repository.UserAuthoritiesRepository;
import com.nineone.nocm.repository.UserRepository;
import com.nineone.nocm.util.GoogleMailSender;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Component
public class AopConfig {

    @Autowired
    private UserAuthoritiesRepository authoritiesRepository;
    @Autowired
    private HttpSession httpSession;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private GoogleMailSender googleMailSender;
//    @Autowired
//    private ChannelRepository channelRepository;

    @Pointcut("execution(* com.nineone.nocm.controller.api.UserApiController.getSessionUser(..))")
    public void getSessionUser(){}

    @Pointcut("execution(* com.nineone.nocm.controller.api.UserApiController.userInfo(..))")
    public void userInfo(){}

    @Pointcut("execution(* com.nineone.nocm.controller.api.UserApiController.rolesUserList(..))")
    public void roleUserList(){}



    @Around("roleUserList()")
    public Object checkUserAdmin(ProceedingJoinPoint joinPoint) throws Throwable{
        User user = (User) joinPoint.getArgs()[0];
        List<String> roles = authoritiesRepository.getUserRoles(user.getEmail());
        if (roles.stream().anyMatch(role -> role.equals("ROLE_ADMIN"))){
            return joinPoint.proceed();
        }else{
            return new ResponseEntity<>("{}",HttpStatus.BAD_REQUEST);
        }
    }


    @Around("userInfo()||getSessionUser()")
    public Object CheckUserRole(ProceedingJoinPoint joinPoint) throws Throwable{
        User user = (User)joinPoint.getArgs()[0];
        user.setRoles(authoritiesRepository.getUserRoles(user.getEmail()));
        for (String str : user.getRoles()){
            log.info("user :"+user.getName()+" role : "+str);
        }
        return joinPoint.proceed();
//        if (user.getRoles().isEmpty()){
//            return joinPoint.proceed();
//        }
//        for (String role : user.getRoles()){
//            if (role.equals("ROLE_ADMIN")){
//                return joinPoint.proceed();
//            }
//            if (role.equals("ROLE_USER")){
//                return joinPoint.proceed();
//            }
//        }
//        return new ResponseEntity<>("{}",HttpStatus.BAD_REQUEST);

//        if (user.getName().equals("원주민")){
//            return joinPoint.proceed();
//        }else{
//            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
//        }
    }
}
