package com.Library_management_system.service.impl;

import com.Library_management_system.Enums.Operator;
import com.Library_management_system.dto.response.BookFilterResponse;
import com.Library_management_system.model.Book;
import com.Library_management_system.repositories.BookRepository;
import com.Library_management_system.service.BookFilterFactory;
import com.Library_management_system.service.BookFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class BookNoFilterImpl implements BookFilterStrategy {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookFilterResponse> getFilteredBook(Operator operator, String value) {
        if(!operator.equals(Operator.EQUALS)){
            throw new IllegalArgumentException("Only Equals is expected with book no");
        }
      List<Book> books =  bookRepository.findByBookNo(value);
        return books.stream()
                .map(book -> convertToBookFilterResponse(book))
                .collect(Collectors.toList());
    }

    private BookFilterResponse convertToBookFilterResponse(Book book)
    {
        return BookFilterResponse
                .builder()
                .bookNo(book.getBookNo())
                .bookTitle(book.getTitle())
                .authorName(book.getAuthor().getName())
                .authorEmail(book.getAuthor().getEmail())
                .bookType(book.getBookType())
                .build();
    }
}
