package com.Library_management_system.service.impl;

import com.Library_management_system.Enums.BookFilter;
import com.Library_management_system.Enums.Operator;
import com.Library_management_system.dto.request.BookCreationRequest;
import com.Library_management_system.dto.response.BookCreationResponse;
import com.Library_management_system.dto.response.BookFilterResponse;
import com.Library_management_system.model.Author;
import com.Library_management_system.model.Book;
import com.Library_management_system.model.User;
import com.Library_management_system.repositories.BookRepository;
import com.Library_management_system.service.BookFilterFactory;
import com.Library_management_system.service.BookFilterStrategy;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService
{
    @Autowired
    private BookRepository bookRepository;

     @Autowired
     private AuthorService authorService;

     @Autowired
     private BookFilterFactory bookFilterFactory;

    public BookCreationResponse addBook(BookCreationRequest request)
    {
        Author authorFromDb = authorService.findAuthorInDb(request.getAuthorEmail());
        //if we use cascase we dont need to write it
//        if(authorFromDb == null){
//            authorFromDb = authorService.saveMyAuthor(Author.builder().
//                    id(UUID.randomUUID().toString())
//                            .email(request.getAuthorEmail())
//                            .name(request.getAuthorName())
//                    .build());
//        }
        Book book = request.toBook();
        //Using Cascade
        if(authorFromDb == null){
            authorFromDb = Author.builder().
                    id(UUID.randomUUID().toString())
                            .email(request.getAuthorEmail())
                            .name(request.getAuthorName())
                    .build();
        }
        book.setAuthor(authorFromDb);
        book = bookRepository.save(book);
        return BookCreationResponse.builder()
                .bookNo(book.getBookNo())
                .bookTitle(book.getTitle())
                .securityAmount(book.getSecurityAmount())
                .build();
    }

    public List<BookFilterResponse> filterBook(BookFilter filterBy, Operator operator, String value)
    {
        BookFilterStrategy strategy = bookFilterFactory.getStrategy(filterBy);
        return strategy.getFilteredBook(operator,value);
    }

    public Book checkIfBookIsValid(String bookNo)
    {
        List<Book> books = bookRepository.findByBookNo(bookNo);
        if(books.isEmpty()){
            return null;
        }
        return books.get(0);
    }

    public void markBookUnavailable(Book bookFromDb, User userFromDb) {
        bookFromDb.setUser(userFromDb);
        bookRepository.save(bookFromDb);
    }
}
