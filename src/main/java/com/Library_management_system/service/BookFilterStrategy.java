package com.Library_management_system.service;

import com.Library_management_system.Enums.Operator;
import com.Library_management_system.dto.response.BookFilterResponse;

import java.util.List;

public interface BookFilterStrategy
{
    List<BookFilterResponse> getFilteredBook(Operator operator, String value);
}
