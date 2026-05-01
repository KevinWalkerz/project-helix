package com.helix.zibrary.data.security.repositories;

import com.helix.zibrary.data.security.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, UUID>, JpaSpecificationExecutor<UserRoles> {

    @Modifying
    @Query("DELETE FROM UserRoles ur WHERE ur.user.id=:userId")
    void deleteUserRolesByUserId(@Param("userId") UUID userId);

}
