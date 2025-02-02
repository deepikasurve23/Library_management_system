package com.Library_management_system.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorTest
{
    //If i want to test that my getter is working fine, it will have to set the param first
    @Test
    public void testGetEmail(){
        //Check something here , nothing to be returned so always return type will be void
        Author author = new Author();
        author.setEmail("a@gmail.com");
        assertEquals("a@gmail.com",author.getEmail());
    }
}
