package com.Library_management_system.controller;

import com.Library_management_system.Exception.BookException;
import com.Library_management_system.Exception.UserException;
import com.Library_management_system.dto.request.TxnRequest;
import com.Library_management_system.dto.request.TxnReturnRequest;
import com.Library_management_system.service.impl.TxnService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
//@Validated
@RequestMapping("/txn")
public class TxnController
{
   @Autowired
   private TxnService txnService;

   public static Log logger = LogFactory.getLog("TxnController.class");

   @PostMapping("/issue")
   public ResponseEntity<String> createTxn(@RequestBody @Validated TxnRequest txnRequest) throws UserException, BookException {
       String txnId =  txnService.createTxn(txnRequest);
       if(txnId != null || !txnId.isEmpty()){
          return new ResponseEntity<>(txnId, HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @PutMapping("/return")
   public Double returnTxn(@RequestBody TxnReturnRequest txnReturnRequest ) throws BookException, UserException {
      return txnService.returnTxn(txnReturnRequest);
   }

}
