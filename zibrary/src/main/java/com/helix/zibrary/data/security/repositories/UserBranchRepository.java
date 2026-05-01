package com.helix.zibrary.data.security.repositories;

import com.helix.zibrary.data.security.entities.UserBranch;
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
public interface UserBranchRepository extends JpaRepository<UserBranch, UUID>, JpaSpecificationExecutor<UserBranch> {

    @Query("SELECT ucb FROM UserBranch ucb WHERE ucb.user.id =:userId ORDER BY ucb.branch.name ASC")
    List<UserBranch> findUserCompanyBranchByUserId(@Param("userId") UUID userId);
    
    @Modifying
    @Query("DELETE FROM UserBranch ucb WHERE ucb.user.id =:userId")
    void deleteUserBranchById(@Param("userId") UUID userId);

}
