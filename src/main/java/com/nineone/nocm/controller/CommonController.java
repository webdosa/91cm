package com.nineone.nocm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class CommonController implements ErrorController {

    @RequestMapping("/error")
    public String errorHandler(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);


        if (status != null){
            HttpStatus statusCode = HttpStatus.valueOf(Integer.valueOf(status.toString()));
            log.error("Request error : "+status.toString());
            switch (statusCode){
                case NOT_FOUND:
                    return "error/404";
                case INTERNAL_SERVER_ERROR:
                    return "error/500";
                case FORBIDDEN:
                    return "error/403";
                case METHOD_NOT_ALLOWED:
                    return "error/405";
                default:
                    return "error/error";
            }
        }
        return "error/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
