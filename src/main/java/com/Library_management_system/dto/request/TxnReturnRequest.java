package com.Library_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TxnReturnRequest {
    @NotBlank(message = "Book no should not be blank")
    private String bookNo;

    @NotBlank(message = "Txn ID should not be blank")
    private String txnId;

    @NotBlank(message = "User email should not be blank")
    private String userEmail;
}

