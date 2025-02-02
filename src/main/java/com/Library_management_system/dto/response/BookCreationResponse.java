package com.Library_management_system.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookCreationResponse
{
    private String bookNo;

    private String bookTitle;

    private Double securityAmount;
}
