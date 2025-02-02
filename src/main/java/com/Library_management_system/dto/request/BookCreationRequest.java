package com.Library_management_system.dto.request;

import com.Library_management_system.Enums.BookType;
import com.Library_management_system.model.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookCreationRequest
{
    @NotBlank(message = "Book title must not be blank")
    private String bookTitle;

    @Positive(message = "Positive values are expected")
    private Double securityAmount;

    @NotBlank(message = "Book Number must not be blank")
    private String bookNo;

    @NotNull(message = "Book Type must not be null")
    private BookType bookType;

    @NotBlank(message = "Author name must not be blank")
    private String AuthorName;

    @NotBlank(message = "Author email must not be blank")
    private String authorEmail;


    public Book toBook()
    {
        return Book.builder()
                .title(this.bookTitle)
                .securityAmount(this.securityAmount)
                .bookNo(this.bookNo)
                .bookType(BookType.EDUCATIONAL)
                .build();
    }
}
