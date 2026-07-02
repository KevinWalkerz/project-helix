package com.helix.zibrary.data.global.repositories;

import com.helix.zibrary.data.global.entities.ProgramSetup;
import com.helix.zibrary.data.image.entities.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramSetupRepository extends JpaRepository<ProgramSetup, String>, JpaSpecificationExecutor<ProgramSetup> {

}
