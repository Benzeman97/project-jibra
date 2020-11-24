package com.benz.authorization.server.api.model;

import com.benz.authorization.server.api.entity.User;
import com.benz.authorization.server.api.entity.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthUserDetails implements UserDetails {

    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean accNonExpired;
    private boolean accNonLocked;
    private boolean credentialNonExpired;
    private boolean active;

    public AuthUserDetails(boolean active,List<GrantedAuthority> authorities,String username,boolean accNonExpired,
                           boolean accNonLocked,String password,boolean credentialNonExpired)
    {
        this.username=username;
        this.password=password;
        this.authorities=authorities;
        this.accNonExpired=accNonExpired;
        this.accNonLocked=accNonLocked;
        this.credentialNonExpired=credentialNonExpired;
        this.active=active;
    }

    public static UserDetails builder(User user)
    {
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();

        user.getRoles().forEach(role->{
               grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name()));

               role.getPermissions().forEach(perm->{
                   grantedAuthorities.add(new SimpleGrantedAuthority(perm.getName().name()));
               });
        });

        UserStatus userStatus = user.getUserStatus();

        return new AuthUserDetails((userStatus.getActive()==1),grantedAuthorities,user.getEmail(),(userStatus.getAccNonExpired()==1),
                (userStatus.getAccNonLocked()==1),user.getPassword(),(userStatus.getCredentialNonExpired()==1));
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
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
