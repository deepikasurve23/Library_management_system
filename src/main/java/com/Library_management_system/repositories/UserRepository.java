package com.Library_management_system.repositories;

import com.Library_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>
{
    //1 - way which u can write the method name and it is going to return u the response
    //search via name
    List<User> findByName(String name);

    //search like name
    List<User> findByNameLike(String name);
//    List<User> findByEmail(String email);
//    List<User> findByEmailContains(String email);
   // List<User> findByNameAndEmail(String name, String email);

    //2 - way to writing the queries - not native JPQL
    //By defqault native Query is false
    //If native Query is false i am writing to hibernate to check my query
    @Query(value = "SELECT u FROM User u WHERE u.name = :name", nativeQuery = false)
    List<User> retreiveUserViaName(String name);

    //3 way - native
    @Query(value = "select * from User where name:=name", nativeQuery = true)
    List<User> retreiveUserViaNameNative(String name);

    User findByEmail(String email);
}
