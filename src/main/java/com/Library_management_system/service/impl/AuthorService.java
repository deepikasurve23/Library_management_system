package com.Library_management_system.service.impl;

import com.Library_management_system.model.Author;
import com.Library_management_system.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService
{
    @Autowired
    private AuthorRepository authorRepository;

    public Author findAuthorInDb(String authorEmail)
    {
       return authorRepository.findByEmail(authorEmail);
    }

    public Author saveMyAuthor(Author author)
    {
        return authorRepository.save(author);
    }
}
