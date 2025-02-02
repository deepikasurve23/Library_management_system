package com.Library_management_system.service.impl;

import com.Library_management_system.Enums.TxnStaus;
import com.Library_management_system.Exception.BookException;
import com.Library_management_system.Exception.TxnException;
import com.Library_management_system.Exception.UserException;
import com.Library_management_system.dto.request.TxnRequest;
import com.Library_management_system.dto.request.TxnReturnRequest;
import com.Library_management_system.model.Book;
import com.Library_management_system.model.Txn;
import com.Library_management_system.model.User;
import com.Library_management_system.repositories.TxnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService
{
    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Value("${user.valid.days}")
    private int validUpto;

    @Value("${user.delayed.finePerDay}")
    private int finePerDay;

    @Transactional(rollbackFor = {BookException.class, UserException.class})
    public String createTxn(TxnRequest txnRequest) throws UserException, BookException {
       //user is trying to get the book, user is valid or not?
        User userFromDb = userService.checkIfUserIsValid(txnRequest.getUserEmail());
        if(userFromDb == null){
            throw new UserException("User is not valid");
        }

        //book no he is asking, actually belongs to my library
        Book bookFromDb = bookService.checkIfBookIsValid(txnRequest.getBookNo());
        if(bookFromDb == null){
            throw new BookException("Book is not valid");
        }

        //book a user is asking should not be assigned to some another user
        if(bookFromDb.getUser() != null){
            throw new BookException("book is not free to be issued.");
        }
        return createTxn(userFromDb, bookFromDb);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String createTxn(User userFromDb, Book bookFromDb) throws BookException {
        //create a txn
        String txnId = UUID.randomUUID().toString();
        Txn txn = Txn.builder().
                txnId(txnId).
                user(userFromDb).
                book(bookFromDb).
                txnStaus(TxnStaus.ISSUED).
                issuedDate(new Date()).
                build();

        txnRepository.save(txn);

//        if(txn.getSettlementAmount()==null){
//            throw new BookException("Exception has been thrown");
//        }

        bookService.markBookUnavailable(bookFromDb, userFromDb);
        return txnId;
    }

    @Transactional(rollbackFor = {UserException.class, BookException.class})
    public Double returnTxn(TxnReturnRequest txnReturnRequest) throws UserException, BookException {
        //user is trying to get the book, user is valid or not?
        User userFromDb = userService.checkIfUserIsValid(txnReturnRequest.getUserEmail());
        if(userFromDb == null){
            throw new UserException("User is not valid");
        }

        //book no he is asking, actually belongs to my library
        Book bookFromDb = bookService.checkIfBookIsValid(txnReturnRequest.getBookNo());
        if(bookFromDb == null){
            throw new BookException("Book is not valid");
        }

        if(bookFromDb.getUser() != null && bookFromDb.getUser().equals(userFromDb)){
            Txn txnFromDb = txnRepository.findByTxnId(txnReturnRequest.getTxnId());
            if(txnFromDb == null){
                throw new TxnException("No Transaction found in my db with this txnId");
            }

            Double amount = calculateSettlementAmout(txnFromDb, bookFromDb);

            if(amount == bookFromDb.getSecurityAmount())
                txnFromDb.setTxnStaus(TxnStaus.RETURNED);
            else
                txnFromDb.setTxnStaus(TxnStaus.FINED);

            txnFromDb.setSettlementAmount(amount);

            //Mark the book is available
            bookFromDb.setUser(null);
            txnRepository.save(txnFromDb);
            return  amount;
        }else{
            throw new TxnException("Book is assigned to someone else, or not at all assigned to you");
        }
    }

    //No need to add @trnsactional here because there is no sql type operation performed
    public Double calculateSettlementAmout(Txn txnFromDb, Book bookFromDb)
    {
        long issueTime = txnFromDb.getIssuedDate().getTime();
        long returnTime = System.currentTimeMillis();
        long diff = returnTime - issueTime;

        int daysPassed = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        if(daysPassed > validUpto){
            int fineAmount = (daysPassed - validUpto)*finePerDay;
            return bookFromDb.getSecurityAmount() - fineAmount;
        }
        return bookFromDb.getSecurityAmount();
    }
}

//txnRepository.save(txn);
//bookService.markBookUnavailable(bookFromDb, userFromDb);
//Above there are 2 queries if one query is run or second one is not due to issue so we had not getting right data
//so resolve this issue we used @Transactional
//using this data saved fully or not saved fully