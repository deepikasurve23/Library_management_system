package com.Library_management_system.repositories;

import com.Library_management_system.model.Author;
import org.hibernate.id.factory.internal.AutoGenerationTypeStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {
        "spring.datasource.url = jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto = create-drop"
})
public class AuthorRepositoryTest
{
    @Autowired
    private AuthorRepository authorRepository;

    //Always run first when this class is loaded
    @BeforeEach
    public void setUp(){
        Author author = Author.builder().id("1").email("a@gmail.com").name("a1").build();
        authorRepository.save(author);
        Author author1 = Author.builder().id("2").email("b@gmail.com").name("a2").build();
        authorRepository.save(author1);
        Author author2 = Author.builder().id("3").email("c@gmail.com").name("a3").build();
        authorRepository.save(author2);
    }

    @Test
    public void tesFindByEmail(){
        Author author = authorRepository.findByEmail("a@gmail.com");
        assertEquals(author.getEmail(),"a@gmail.com");

    }

}

//Need one database here which is in memory database : h2
//My test is going to be looking , return me the response from my method

//For my sql junit test case but generally we use h2 for test cases when we connect our application with SQL
//@DataJpaTest(properties = {
//        "spring.datasource.url = jdbc:mysql://localhost:3306/library_management?createDatabaseIfNotExist=true",
//        "spring.datasource.username = root",
//        "spring.datasource.password = root",
//        "spring.jpa.hibernate.ddl-auto = update"
//})