package com.Library_management_system.dto.response;

import com.Library_management_system.Enums.BookType;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookFilterResponse
{
    private String bookNo;
    private String bookTitle;
    private BookType bookType;
    private String authorName;
    private String authorEmail;
}
