package com.Library_management_system.service.impl;

import com.Library_management_system.Enums.Operator;
import com.Library_management_system.dto.response.BookFilterResponse;
import com.Library_management_system.service.BookFilterStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookTypeFilterImpl implements BookFilterStrategy {
    @Override
    public List<BookFilterResponse> getFilteredBook(Operator operator, String value) {
        return List.of();
    }
}
