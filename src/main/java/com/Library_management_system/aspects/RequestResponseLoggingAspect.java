package com.Library_management_system.aspects;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


//Step - 1
@Aspect
@Component
public class RequestResponseLoggingAspect
{
    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingAspect.class);

     //Step 2 - Define point cot
    @Pointcut("execution(* com.Library_management_system.controller..*(..))")
    public void controllerMethods(){}  // declaration, marker, use this for multiple advices

    //Step 3 - create Advice and used point cut in this
    @Before("controllerMethods()")
  //  @Before("execute(* com.Library_management_system.controller..*(..))")
    public void logRequest(){
       HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        log.info("Request : "+ request.getMethod() + " " + request.getRequestURI());
        log.info("Request Headers : ");
         request.getHeaderNames().asIterator().forEachRemaining(header ->
                 log.info(header+ ":"+ request.getHeader(header                                     )));
    }

    @AfterReturning(value = "controllerMethods()", returning = "response")
    public void logResponse(Object response){
        if(response instanceof HttpServletResponse){
            HttpServletResponse response1 = (HttpServletResponse) response;
            log.info("Response Status : "+ response1.getStatus());
        }else{
            log.info("Response : "+response.toString());
        }
    }

}

//execute(* com.Library_management_system.controller..*(..))
//1st * represents - return type which is anything
//2nd * represents - any function
//1st .. represents any class in controller
//2nd .. represent any parameter
