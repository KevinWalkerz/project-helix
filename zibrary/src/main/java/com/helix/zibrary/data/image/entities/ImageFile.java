package com.helix.zibrary.data.image.entities;

import com.helix.zibrary.enumeration.image.ImageStoreType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "source_image_file")
@Getter
@Setter
public class ImageFile{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fileName;

    @Enumerated(EnumType.STRING)
    private ImageStoreType storeType;

    private int fileSize;

    private String filePath; // relative path, LOCAL_STORAGE -> path storage, CLOUD_STORAGE -> path cloud storage
}
