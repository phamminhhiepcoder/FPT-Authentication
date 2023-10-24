package com.example.authenticationspring.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUser extends User {

    private static final long serialVersionUID = 1L;

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired,
                          boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
                          String firstName, String lastName, String emailaddress) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullname = firstName + " " + lastName;
        this.emailaddress = emailaddress;
    }

    private String firstName;
    private String lastName;
    private String fullname;
    private String emailaddress;

    @Override
    public String toString() {
        return "MySecurityUser firstName=" + firstName + ", lastName=" + lastName + ", name=" + fullname + ", emailaddress=" + emailaddress
                + "] " + super.toString();
    }
}