package com.Library_management_system.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TxnRequest
{
    @NotNull(message = "Email should not be blank")
    private String userEmail;

    @NotNull(message = "Book Number should not be blank")
    private String bookNo;

}
