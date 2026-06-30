package com.helix.security;

import com.helix.zibrary.data.security.entities.Privilege;
import com.helix.zibrary.data.security.entities.Role;
import com.helix.zibrary.data.security.entities.RolePrivileges;
import com.helix.zibrary.data.security.entities.UserCredentials;
import com.helix.zibrary.data.security.entities.UserRoles;
import com.helix.zibrary.data.security.repositories.RolePrivilegesRepository;
import com.helix.zibrary.data.security.repositories.UserCredentialsRepository;
import com.helix.zibrary.data.security.repositories.UserRolesRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final UserRolesRepository userRolesRepository;
    private final RolePrivilegesRepository rolePrivilegesRepository;

    public UserDetailsServiceImpl(UserCredentialsRepository userCredentialsRepository,
                                  UserRolesRepository userRolesRepository,
                                  RolePrivilegesRepository rolePrivilegesRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.userRolesRepository = userRolesRepository;
        this.rolePrivilegesRepository = rolePrivilegesRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserCredentials userCredentials = findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));

        return User.withUsername(userCredentials.getUsername())
                .password(userCredentials.getHashedPassword())
                .disabled(!userCredentials.isActive())
                .authorities(loadAuthorities(userCredentials))
                .build();
    }

    private Optional<UserCredentials> findByUsernameOrEmail(String usernameOrEmail) {
        return userCredentialsRepository.findUserByEmail(usernameOrEmail)
                .or(() -> userCredentialsRepository.findUserByUsername(usernameOrEmail));
    }

    private Collection<? extends GrantedAuthority> loadAuthorities(UserCredentials userCredentials) {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        Collection<UserRoles> userRoles = userRolesRepository.findByUserId(userCredentials.getId());

        for (UserRoles userRole : userRoles) {
            Role role = userRole.getRole();
            if (role == null || !role.isActive()) {
                continue;
            }

            authorities.add(new SimpleGrantedAuthority("ROLE_" + normalize(role.getName())));

            Collection<RolePrivileges> rolePrivileges = rolePrivilegesRepository.findByRoleIdAndAllowTrue(role.getId());
            for (RolePrivileges rolePrivilege : rolePrivileges) {
                Privilege privilege = rolePrivilege.getPrivilege();
                if (privilege != null) {
                    authorities.add(new SimpleGrantedAuthority(createPrivilegeAuthority(privilege)));
                }
            }
        }

        return authorities;
    }

    private String createPrivilegeAuthority(Privilege privilege) {
        String pageName = normalize(privilege.getPageName());
        String accessType = privilege.getAccessType() == null ? "ACCESS" : privilege.getAccessType().name();
        return pageName + "_" + accessType;
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return "UNNAMED";
        }
        return value.trim()
                .toUpperCase(Locale.ROOT)
                .replaceAll("[^A-Z0-9]+", "_")
                .replaceAll("^_+|_+$", "");
    }
}
