package com.helix.zibrary.data.global.entities;

import com.helix.zibrary.enumeration.image.ImageStoreType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "glo_setup")
@Getter
@Setter
public class ProgramSetup {

    @Id
    private Long id;

    //Company Info
    private String companyAcronym;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String companyEmail;
    private String companyWebsite;

    //Image Logo

    //Program Info
    private String programName;

    @Enumerated(EnumType.STRING)
    private ImageStoreType imageStoreType;

}
