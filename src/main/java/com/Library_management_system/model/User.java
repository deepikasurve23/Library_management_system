package com.Library_management_system.model;

import com.Library_management_system.Enums.UserStatus;
import com.Library_management_system.Enums.UserType;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Table(name = "\"USER\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User extends TimeStamps
{
    //Primary key of Table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String name;

    @Column(unique = true, length = 15)
    private String phoneNo;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    private String address;

    @Enumerated
    private UserStatus userStatus;

    //@Enumerated(value = EnumType.STRING)
    @Enumerated
    private UserType userType;

    @OneToMany(mappedBy = "user")
    private List<Book> bookList;

    @OneToMany(mappedBy = "user")
    private List<Txn> txnList;

}

//@OneToMany- First One represents User Class, many represents Book Class
