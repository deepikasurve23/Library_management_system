package com.Library_management_system.service.impl;

import com.Library_management_system.Enums.Operator;
import com.Library_management_system.Enums.UserFilter;
import com.Library_management_system.dto.request.UserCreationRequest;
import com.Library_management_system.dto.response.UserCreationResponse;
import com.Library_management_system.Enums.UserType;
import com.Library_management_system.model.User;
import com.Library_management_system.repositories.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
{

    @Autowired
    private UserRepository userRepository;

    public UserCreationResponse addStudent(UserCreationRequest request)
    {
        //UserCreationRequest convert into user
        User user = request.toUser();
        System.out.println(user.getUserStatus());
        user.setUserType(UserType.STUDENT);
        System.out.println(user.getUserType());
        User userFromDb = userRepository.save(user);
        //Convert User to UserCreationResponse
        return UserCreationResponse.builder()
                .userName(userFromDb.getName())
                .userEmail(userFromDb.getEmail())
                .userAddress(userFromDb.getAddress())
                .userPhone(userFromDb.getPhoneNo())
                .build();
    }

    public List<User> filterBy(UserFilter filterBy, Operator operator, String value)
    {
        switch(filterBy){
            case NAME ->{
                switch(operator){
                    case EQUALS -> userRepository.findByName(value);
                    case LIKE -> userRepository.findByNameLike("%"+value+"%");
                }
            }
        }

        return new ArrayList<>();
    }

    public User checkIfUserIsValid(String userEmail)
    {
        return userRepository.findByEmail(userEmail);
    }
}
