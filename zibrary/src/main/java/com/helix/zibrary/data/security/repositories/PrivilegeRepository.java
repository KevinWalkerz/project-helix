package com.helix.zibrary.data.security.repositories;

import com.helix.zibrary.data.security.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, UUID>, JpaSpecificationExecutor<Privilege> {

    @Query("SELECT p FROM Privilege p WHERE p.id IN (SELECT rp.privilege.id FROM RolePrivileges rp WHERE rp.role.id = :roleId AND rp.isAllowed = TRUE)")
    List<Privilege> getListPrivilegesByRoleId(@Param("roleId") UUID roleId);

}
