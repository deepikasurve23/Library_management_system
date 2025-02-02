package com.Library_management_system.model;

import com.Library_management_system.Enums.BookType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Book extends TimeStamps
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String title;

    @Column(length = 20, unique = true)
    private String bookNo;

    @Enumerated
    private BookType bookType;

    @Column(nullable = false)
    private Double securityAmount;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    //composite key
    @JoinColumns({
            @JoinColumn(name = "author_id",referencedColumnName = "id"),
            @JoinColumn(name="author_email", referencedColumnName = "email")
    })
    //@JsonIgnore - if want to ignnore whole Object in my response
  //  @JsonIgnoreProperties(value = "bookList") //If want to ignore only bookList proprty which is present in author object
    private Author author;

    @OneToMany(mappedBy = "book")
    private List<Txn> txnList;
}

