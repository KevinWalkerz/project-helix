package com.helix.zibrary.data.security.services;

import com.helix.zibrary.data.security.entities.Privilege;
import com.helix.zibrary.data.security.repositories.PrivilegeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository){
        this.privilegeRepository = privilegeRepository;
    }

    public Optional<Privilege> getPrivilege(UUID id) {
        return privilegeRepository.findById(id);
    }

    public Page<Privilege> findPrivilegeContainer(Pageable pageable) {
        Page<Privilege> results = null;
        try {
            results = privilegeRepository.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Privilege> getListPrivilegesByRoleId(UUID roleId){
        List<Privilege> result = null;
        try{
            result = privilegeRepository.getListPrivilegesByRoleId(roleId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
