package com.Library_management_system.controller;

import com.Library_management_system.Enums.Operator;
import com.Library_management_system.Enums.UserFilter;
import com.Library_management_system.dto.request.UserCreationRequest;
import com.Library_management_system.dto.response.UserCreationResponse;
import com.Library_management_system.model.User;
import com.Library_management_system.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;

   @PostMapping("/addStudent")
    public UserCreationResponse addStudent(@RequestBody @Validated UserCreationRequest request){
        return userService.addStudent(request);
    }

    @GetMapping("/filterStudent")
    public List<User> filteredUser(@RequestParam("filterBy") UserFilter filterBy,
                               @RequestParam("operator") Operator operator,
                               @RequestParam ("value") String value){
       return userService.filterBy(filterBy, operator, value);
    }
}
