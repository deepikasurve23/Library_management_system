package com.Library_management_system.repositories;

import com.Library_management_system.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>
{
    List<Book> findByBookNo(String bookNo);
}
