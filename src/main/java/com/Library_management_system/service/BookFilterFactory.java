package com.Library_management_system.service;

import com.Library_management_system.Enums.BookFilter;
import com.Library_management_system.dto.response.BookCreationResponse;
import com.Library_management_system.service.impl.BookNoFilterImpl;
import com.Library_management_system.service.impl.BookTitleFilterImpl;
import com.Library_management_system.service.impl.BookTypeFilterImpl;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BookFilterFactory
{
    private final Map<BookFilter, BookFilterStrategy> strategies = new HashMap<>();

    public BookFilterFactory(BookNoFilterImpl bookNoFilter, BookTitleFilterImpl bookTitleFilter, BookTypeFilterImpl bookTypeFilter){
        strategies.put(BookFilter.BOOK_NO, bookNoFilter);
        strategies.put(BookFilter.BOOK_TITLE,bookTitleFilter);
        strategies.put(BookFilter.BOOK_TYPE,bookTypeFilter);
    }

    public BookFilterStrategy getStrategy(BookFilter bookFilter){
        return strategies.get(bookFilter);
    }
}
