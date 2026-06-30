package com.helix.zibrary.data.security.repositories;

import com.helix.zibrary.data.security.dto.RolePrivilegesDTO;
import com.helix.zibrary.data.security.entities.RolePrivileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface RolePrivilegesRepository extends JpaRepository<RolePrivileges, UUID>, JpaSpecificationExecutor<RolePrivileges> {

    Collection<RolePrivileges> findByRoleIdAndAllowTrue(UUID roleId);

    @Query("SELECT RolePrivilegesDTO(p.id, p.menuName, p.subMenuName, p.pageName, CASE WHEN rp.id IS NOT NULL THEN true ELSE false END) " +
            "FROM Privilege p " +
            "LEFT JOIN RolePrivileges rp ON rp.privilege.id = p.id AND rp.role.id = :roleId")
    List<RolePrivilegesDTO> listPrivilegesByRoleId(@Param("roleId") Long roleId);

    @Modifying
    void deleteRolePrivilegesByRoleId(@Param("roleId") UUID roleId);
}
