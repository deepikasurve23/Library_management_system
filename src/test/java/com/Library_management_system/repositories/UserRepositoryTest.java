package com.Library_management_system.repositories;

import com.Library_management_system.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testDb",
        "spring.jpa.hibernate.ddl-auto = create-drop"
})
public class UserRepositoryTest
{
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        User user = User.builder().name("Mohit").email("mohit@gmail.com").build();
        userRepository.save(user);

        User user1 = User.builder().name("Mohit").email("mohit1@gmail.com").build();
        userRepository.save(user1);
    }

    @Test
    public void testFindByName(){
        List<User> users = userRepository.findByName("Mohit");
        List<User> expectedList = new ArrayList<>();
        User u1 = User.builder().id(1).name("Mohit").email("mohit@gmail.com").build();
        User u2 = User.builder().id(2).name("Mohit").email("mohit@gmail.com").build();
        expectedList.add(u1);
        expectedList.add(u2);
        assertEquals(expectedList.size(), users.size());
    }

}
