package com.Library_management_system.dto.request;

import com.Library_management_system.Enums.UserStatus;
import com.Library_management_system.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreationRequest
{
    private String userName;

    @NotBlank(message = "User email must not be blank")
    private String userEmail;

    private String userAddress;

    private String userPhone;

    private String password;

    public User toUser()
    {
        return User.builder()
                .name(this.userName)
                .email(this.userEmail)
                .phoneNo(this.userPhone)
                .address(this.userAddress)
                .userStatus(UserStatus.ACTIVE)
                .build();
    }
}
