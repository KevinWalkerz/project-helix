package com.helix.zibrary.data.image.repositories;

import com.helix.zibrary.data.image.entities.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, UUID>, JpaSpecificationExecutor<ImageFile> {
}
