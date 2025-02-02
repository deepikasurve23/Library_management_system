package com.Library_management_system.repositories;

import com.Library_management_system.model.Author;
import com.Library_management_system.model.AuthorCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, AuthorCompositeKey>
{
    Author findByEmail(String email);
}
