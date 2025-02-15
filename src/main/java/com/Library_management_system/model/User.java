package com.Library_management_system.model;

import com.Library_management_system.Enums.UserStatus;
import com.Library_management_system.Enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "\"USER\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User extends TimeStamps implements UserDetails
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

    private String password;

    private String authorities;

    //Spring Security changes for Authentication it comes from UserDetails Class
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] auth = authorities.split(",");
       return Arrays.stream(auth).map(a -> new SimpleGrantedAuthority(a)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

//@OneToMany- First One represents User Class, many represents Book Class
