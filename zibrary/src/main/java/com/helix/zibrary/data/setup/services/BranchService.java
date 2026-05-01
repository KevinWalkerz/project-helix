package com.helix.zibrary.data.setup.services;

import com.helix.zibrary.data.setup.entities.Branch;
import com.helix.zibrary.data.setup.entities.BranchCriteria;
import com.helix.zibrary.data.setup.repositories.BranchRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository){
        this.branchRepository = branchRepository;
    }

    public Optional<Branch> getBranch(UUID id){
        return branchRepository.findById(id);
    }

    public List<Branch> findContainer(Branch branch, List<UUID> defaultUserAccess){
        return branchRepository.findAll(BranchCriteria.buildCriteria(branch, defaultUserAccess));
    }

    public Page<Branch> findPagingContainer(Pageable pageable, Branch branch, List<UUID> defaultUserAccess){
        return branchRepository.findAll(BranchCriteria.buildCriteria(branch, defaultUserAccess), pageable);
    }

    public Branch saveBranch(Branch entity){
        return branchRepository.save(entity);
    }
}
