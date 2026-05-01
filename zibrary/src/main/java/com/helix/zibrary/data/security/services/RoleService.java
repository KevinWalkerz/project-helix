package com.helix.zibrary.data.security.services;

import com.helix.zibrary.data.security.entities.Role;
import com.helix.zibrary.data.security.entities.RoleCriteria;
import com.helix.zibrary.data.security.repositories.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getRole(UUID id) {
        return roleRepository.findById(id);
    }

    public List<Role> getRolesByUserCredentials(UUID userId) {
        List<Role> result = null;
        try {
            result = roleRepository.getListRoleByUserCredentialsId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Page<Role> findPagingContainer(Pageable pageable, Role role, Boolean activeStatus) {
        Page<Role> results = null;
        try {
            results = roleRepository.findAll(RoleCriteria.buildCriteria(role, activeStatus), pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public Role saveRole(Role entity) {
        return roleRepository.save(entity);
    }

}
