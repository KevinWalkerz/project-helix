package com.helix.zibrary.data.security.repositories;

import com.helix.zibrary.data.security.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, UUID>, JpaSpecificationExecutor<UserCredentials> {

    @Query("SELECT u FROM UserCredentials u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<UserCredentials> findUserByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserCredentials u WHERE u.userName = :userName")
    Optional<UserCredentials> findUserByUsername(@Param("userName") String userName);

}
