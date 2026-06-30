package com.helix.zibrary.data.security.repositories;

import com.helix.zibrary.data.security.entities.UserResetPasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserResetPasswordHistoryRepository extends JpaRepository<UserResetPasswordHistory, UUID>, JpaSpecificationExecutor<UserResetPasswordHistory> {

    @Query("SELECT u FROM UserResetPasswordHistory u WHERE u.user.id = :userCredentialsId ORDER BY u.resetDate DESC")
    List<UserResetPasswordHistory> findUserResetPasswordHistoriesByUser(UUID userId);
}
