package com.helix.zibrary.data.security.services;

import ch.qos.logback.core.joran.spi.ActionException;
import com.helix.zibrary.data.security.dto.RolePrivilegesDTO;
import com.helix.zibrary.data.security.entities.*;
import com.helix.zibrary.data.security.repositories.*;
import com.helix.zibrary.enumeration.general.ActiveStatus;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SecurityService{

    private final RolePrivilegesRepository rolePrivilegesRepository;
    private final RoleRepository roleRepository;
    private final UserRolesRepository userRolesRepository;
    private final UserCredentialsRepository userCredentialsRepository;

    public SecurityService(RolePrivilegesRepository rolePrivilegesRepository,
                           RoleRepository roleRepository,
                           UserRolesRepository userRolesRepository,
                           UserCredentialsRepository userCredentialsRepository) {
        this.rolePrivilegesRepository = rolePrivilegesRepository;
        this.roleRepository = roleRepository;
        this.userRolesRepository = userRolesRepository;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    //USER
    public Optional<UserCredentials> getUserById(UUID id) {
        return userCredentialsRepository.findById(id);
    }

    public Optional<UserCredentials> getUserByEmail(String email) {
        return userCredentialsRepository.findUserByEmail(email);
    }

    public Optional<UserCredentials> getUserByUsername(String username) {
        return userCredentialsRepository.findUserByUsername(username);
    }

    //This will be checked in Application Layer
    public Page<UserCredentials> findUsersByCriteria(UserCredentials search, ActiveStatus activeStatus, Pageable pageable) {
        return userCredentialsRepository.findAll(UserCredentialsCriteria.buildCriteria(search, activeStatus), pageable);
    }

    @Transactional(rollbackOn = Exception.class)
    public void saveUserCredentials(UserCredentials entity, List<Role> selectedRoles, String modifiedBy) {
        if(entity.getId() == null){
            entity.setCreatedBy(modifiedBy);
        }
        entity.setUpdatedBy(modifiedBy);

        UserCredentials userCredentials = userCredentialsRepository.save(entity);
        saveRolesForUser(userCredentials, selectedRoles, modifiedBy);
    }

    //User Roles
    @Transactional(rollbackOn = Exception.class)
    public void deleteAllUserRolesByUserId(UUID userId) {
        userRolesRepository.deleteUserRolesByUserId(userId);
    }

    @Transactional(rollbackOn = Exception.class)
    public void saveRolesForUser(UserCredentials user, List<Role> selectedRoles, String modifiedBy) {
        deleteAllUserRolesByUserId(user.getId());

        List<UserRoles> results = new ArrayList<>();
        selectedRoles.forEach(role -> {results.add(new UserRoles(user, role, modifiedBy));});
        userRolesRepository.saveAll(results);
    }

    //ROLES
    public Optional<Role> getRole(UUID id) {
        return roleRepository.findById(id);
    }

    public List<Role> getRolesByUserCredentials(UUID userId) {
        return roleRepository.getListRoleByUserCredentialsId(userId);
    }

    public Page<Role> findRoleContainerBySearch(Role role, ActiveStatus activeStatus, Pageable pageable) {
        return roleRepository.findAll(RoleCriteria.buildCriteria(role, activeStatus), pageable);
    }

    @Transactional
    public void saveRole(Role entity, List<RolePrivilegesDTO> privilegesDTOList, String modifiedBy) {
        if(entity.getId() == null){
            entity.setCreatedBy(modifiedBy);
        }
        entity.setUpdatedBy(modifiedBy);

        Role role = roleRepository.save(entity);
        savePrivilegesForRole(role, privilegesDTOList, modifiedBy);
    }

    //Role Privileges
    @Transactional(rollbackOn = Exception.class)
    public void deleteAllPrivilegesByRoleId(UUID roleId) {
        rolePrivilegesRepository.deleteRolePrivilegesByRoleId(roleId);
    }

    @Transactional(rollbackOn = Exception.class)
    public void savePrivilegesForRole(Role role, List<RolePrivilegesDTO> privilegesDTOList, String modifiedBy) {
        deleteAllPrivilegesByRoleId(role.getId());

        List<RolePrivileges> results = new ArrayList<>();
        privilegesDTOList.forEach(data -> {results.add(new RolePrivileges(role.getId(), data.getPrivilegeId(), data.isAllow(), modifiedBy));});
        rolePrivilegesRepository.saveAll(results);
    }


}
