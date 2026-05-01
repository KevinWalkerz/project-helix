package com.helix.api.configs.securities.jwtsecurity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.helix.zibrary.data.security.entities.Privilege;
import com.helix.zibrary.data.security.entities.Role;
import com.helix.zibrary.data.security.entities.UserCredentials;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final String userName;
    private final boolean active;

    @JsonIgnore
    private final String hashPassword;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UUID id, String userName, String hashPassword, List<GrantedAuthority> authorities, boolean active) {
        this.id = id;
        this.userName = userName;
        this.hashPassword = hashPassword;
        this.authorities = Collections.unmodifiableList(authorities);
        this.active = active;
    }

    public static UserDetailsImpl build(UserCredentials user, List<Role> listRoles, List<Privilege> listPrivileges) {

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role dataRole : listRoles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + dataRole.getName()));
        }

        for (Privilege dataPrivileges : listPrivileges) {
            authorities.add(new SimpleGrantedAuthority(dataPrivileges.getModuleName() + "_" + dataPrivileges.getAccessType()));
        }

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getHashedPassword(),
                authorities,
                user.isActive());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return hashPassword;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}

