package com.benz.security.web.api.model;

import com.benz.security.web.api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String email;
    private boolean active;
    private List<GrantedAuthority> authorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;

    public CustomUserDetails(String username,String password,boolean active,List<GrantedAuthority> authorities
    ,String email,boolean isAccountNonExpired,boolean isCredentialsNonExpired,boolean isAccountNonLocked)
    {
        this.email=email;
        this.username=username;
        this.password=password;
        this.active=active;
        this.authorities=authorities;
        this.isAccountNonExpired=isAccountNonExpired;
        this.isAccountNonLocked=isCredentialsNonExpired;
        this.isCredentialsNonExpired=isAccountNonLocked;

    }

    public static UserDetails builder(User user)
    {
        List<GrantedAuthority> grantedAuthorities= new ArrayList<>();

        user.getRoles().forEach(role->{
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().getName()));

            role.getPermissions().forEach(perm->{
                 grantedAuthorities.add(new SimpleGrantedAuthority(perm.getName().getName()));
            });
        });
        return new CustomUserDetails(user.getUserName(),user.getPassword(),user.getActive().toLowerCase().equals("y"),grantedAuthorities,
                user.getEmail(),user.getAccNonExpired().toLowerCase().equals("y"),user.getCredentialNonExpired().toLowerCase().equals("y"),
                user.getAccNonLocked().toLowerCase().equals("y"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
       return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
       return active;
    }
}
