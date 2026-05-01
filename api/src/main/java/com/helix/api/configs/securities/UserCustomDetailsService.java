package com.helix.api.configs.securities;

import com.helix.api.configs.securities.jwtsecurity.UserDetailsImpl;
import com.helix.zibrary.data.security.entities.Privilege;
import com.helix.zibrary.data.security.entities.Role;
import com.helix.zibrary.data.security.entities.UserCredentials;
import com.helix.zibrary.data.security.services.PrivilegeService;
import com.helix.zibrary.data.security.services.RoleService;
import com.helix.zibrary.data.security.services.UserCredentialsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCustomDetailsService implements UserDetailsService {

    private final UserCredentialsService userCredentialsService;
    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    public UserCustomDetailsService(UserCredentialsService userCredentialsService,
                                    RoleService roleService,
                                    PrivilegeService privilegeService){
        this.userCredentialsService = userCredentialsService;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserCredentials userCredentials = userCredentialsService.getUserByUsername(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Role> listRoles = roleService.getRolesByUserCredentials(userCredentials.getId());

        List<Privilege> listAllPrivileges = listRoles.stream()
                .flatMap(role -> privilegeService
                        .getListPrivilegesByRoleId(role.getId()).stream())
                .distinct()
                .collect(Collectors.toList());

        return UserDetailsImpl.build(userCredentials, listRoles, listAllPrivileges);
    }
}
