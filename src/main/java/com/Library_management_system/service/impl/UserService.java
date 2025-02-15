package com.Library_management_system.service.impl;

import com.Library_management_system.Enums.Operator;
import com.Library_management_system.Enums.UserFilter;
import com.Library_management_system.dto.request.UserCreationRequest;
import com.Library_management_system.dto.response.UserCreationResponse;
import com.Library_management_system.Enums.UserType;
import com.Library_management_system.model.User;
import com.Library_management_system.repositories.UserCacheRepository;
import com.Library_management_system.repositories.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserCacheRepository userCacheRepository;

    @Value("${student.authority}")
    private String studentAuthority;

    @Value("${admin.authority}")
    private String adminAuthority;

    public UserCreationResponse addStudent(UserCreationRequest request)
    {
        //UserCreationRequest convert into user
        User user = request.toUser();
        user.setUserType(UserType.STUDENT);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(studentAuthority);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //Check first in redis/cache
        User user = userCacheRepository.getUser(email);
        if(user != null){
           return null;
        }
        //check in db
        user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("No user found!!");
        }
        //set in redis
        userCacheRepository.setUser(email, user);
        return user;
    }

    public UserCreationResponse addAdmin(UserCreationRequest request)
    {
        //UserCreationRequest convert into user
        User user = request.toUser();
        user.setUserType(UserType.ADMIN);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(adminAuthority);
        User userFromDb = userRepository.save(user);
        //Convert User to UserCreationResponse
        return UserCreationResponse.builder()
                .userName(userFromDb.getName())
                .userEmail(userFromDb.getEmail())
                .userAddress(userFromDb.getAddress())
                .userPhone(userFromDb.getPhoneNo())
                .build();
    }
}
