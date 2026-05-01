package com.helix.zibrary.data.security.repositories;

import com.helix.zibrary.data.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>, JpaSpecificationExecutor<Role> {

    @Query("SELECT r FROM Role r WHERE r.id IN (SELECT ur.role.id FROM UserRoles ur WHERE ur.user.id = :userId)")
    List<Role> getListRoleByUserCredentialsId(@Param("userId") UUID userId);

    @Query("SELECT r FROM Role r WHERE r.name =:roleName")
    Optional<Role> getRoleFromName(@Param("roleName") String roleName);
}
