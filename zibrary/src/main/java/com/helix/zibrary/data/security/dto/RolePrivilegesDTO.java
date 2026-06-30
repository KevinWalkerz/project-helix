package com.helix.zibrary.data.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RolePrivilegesDTO {
    private UUID privilegeId;
    private String menuName;
    private String subMenuName;
    private String pageName;
    private boolean allow;

    public RolePrivilegesDTO(UUID privilegeId, String menuName, String subMenuName, String pageName, boolean allow){
        this.privilegeId = privilegeId;
        this.menuName = menuName;
        this.subMenuName = subMenuName;
        this.pageName = pageName;
        this.allow = allow;
    }
}
