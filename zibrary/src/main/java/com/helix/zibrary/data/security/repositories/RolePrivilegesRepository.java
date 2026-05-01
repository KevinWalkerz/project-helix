package com.helix.zibrary.data.security.repositories;

import com.helix.zibrary.data.security.entities.RolePrivileges;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolePrivilegesRepository extends JpaRepository<RolePrivileges, UUID>, JpaSpecificationExecutor<RolePrivileges> {

    @Query(value = "select * from fn_role_with_all_privileges(:roleId)", nativeQuery = true)
    List<RolePrivileges> findRolePrivilegesByRoleId(@Param("roleId") UUID roleId);

    @Query(value = "select id, role_id, privilege_id, module_name, name, access_type, allowed from fn_new_role_privileges()", nativeQuery = true)
    List<Object[]> findRolePrivilegesNewRaw();

    @Transactional
    @Modifying
    @Query("DELETE FROM RolePrivileges rp WHERE rp.role.id=:roleId")
    void deleteRolePrivilegesByRoleId(@Param("roleId") UUID roleId);

}
