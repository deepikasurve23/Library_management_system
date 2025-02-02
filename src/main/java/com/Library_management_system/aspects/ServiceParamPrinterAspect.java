package com.Library_management_system.aspects;

import com.Library_management_system.dto.request.TxnRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceParamPrinterAspect
{
    private static final Logger log = LoggerFactory.getLogger(ServiceParamPrinterAspect.class);

    @Pointcut("execution(* com.Library_management_system.service.impl.TxnService.createTxn" +
            "(com.Library_management_system.dto.request.TxnRequest))")
    public void txnServiceCreateMethod(){}

    @Before(value = "txnServiceCreateMethod() && args(txnRequest)")
    public void txnServiceAdvice(TxnRequest txnRequest){
          log.info("txnRequest is : " +txnRequest);
    }


}
