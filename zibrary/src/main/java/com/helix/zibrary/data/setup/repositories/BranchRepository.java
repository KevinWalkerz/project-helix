package com.helix.zibrary.data.setup.repositories;

import com.helix.zibrary.data.setup.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID>, JpaSpecificationExecutor<Branch> {

}
