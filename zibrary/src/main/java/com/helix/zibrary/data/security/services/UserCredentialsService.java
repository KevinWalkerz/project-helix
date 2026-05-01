package com.helix.zibrary.data.security.services;

import com.helix.zibrary.data.enumeration.EnumTaskStatus;
import com.helix.zibrary.data.security.entities.*;
import com.helix.zibrary.data.security.repositories.*;
import com.helix.zibrary.data.setup.entities.Branch;
import com.helix.zibrary.data.setup.services.BranchService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final UserRolesRepository userRolesRepository;
    private final RoleRepository roleRepository;
    private final RolePrivilegesRepository rolePrivilegesRepository;
    private final PrivilegeRepository privilegeRepository;
    private final UserBranchRepository userBranchRepository;

    //Call Company Branch Service
    @Autowired
    private BranchService branchService;

    public UserCredentialsService(UserCredentialsRepository userCredentialsRepository,
                                  UserRolesRepository userRolesRepository,
                                  RoleRepository roleRepository,
                                  RolePrivilegesRepository rolePrivilegesRepository,
                                  PrivilegeRepository privilegeRepository,
                                  UserBranchRepository userBranchRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.userRolesRepository = userRolesRepository;
        this.roleRepository = roleRepository;
        this.rolePrivilegesRepository = rolePrivilegesRepository;
        this.privilegeRepository = privilegeRepository;
        this.userBranchRepository = userBranchRepository;
    }

    /*
    USER CONCEPT
    1. Create Account without Password, but decide the Role -> Status Unverified, Sent email link
    2. Open Email Link -> Set User Name + Password Verified
    3. Save. Status Account to Active and Verified
     */

    /*
                                         ================== USER ==================
     */
    public Optional<UserCredentials> getUserById(UUID id) {
        return userCredentialsRepository.findById(id);
    }

    public Optional<UserCredentials> getUserByEmail(String email) {
        return userCredentialsRepository.findUserByEmail(email);
    }

    public Optional<UserCredentials> getUserByUsername(String userName) {
        return userCredentialsRepository.findUserByUsername(userName);
    }

    public Page<UserCredentials> findPagingContainer(Pageable pageable, UserCredentials userCredentials, Boolean activeStatus){
        return userCredentialsRepository.findAll(UserCredentialsCriteria.buildCriteria(userCredentials, activeStatus), pageable);
    }

    public UserCredentials saveUserCredentials(UserCredentials entity) {
        return userCredentialsRepository.save(entity);
    }


//    //
//    public List<PrivilegeResponse> getPrivilegeStructures() {
//        List<PrivilegeResponse> listPrivilegeDTO = new ArrayList<>();
//        List<RolePrivilegeResponse> listRolePrivileges = getRolePrivilegesNew();
//
//        if (listRolePrivileges != null) {
//            if (!listRolePrivileges.isEmpty()) {
//                for (RolePrivilegeResponse rolePrivilegesData : listRolePrivileges) {
//
//                    PrivilegeResponse dataDTO = new PrivilegeResponse();
//                    dataDTO.setId(rolePrivilegesData.getId());
//                    dataDTO.setPrivilegeId(rolePrivilegesData.getPrivilegeId());
//                    dataDTO.setModuleName(rolePrivilegesData.getModuleName());
//                    dataDTO.setName(rolePrivilegesData.getName());
//                    dataDTO.setAccessType(rolePrivilegesData.getAccessType());
//                    dataDTO.setAllowed(rolePrivilegesData.isAllowed());
//
//                    listPrivilegeDTO.add(dataDTO);
//                }
//            }
//        }
//        return listPrivilegeDTO;
//    }

    /*
                                         ================== USER - ROLES ==================
     */
    @Transactional
    @Modifying
    public void deleteAllUserRolesByUserId(UUID userId) {
        try {
            userRolesRepository.deleteUserRolesByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UserRoles> saveAllUserRoles(UUID userId, List<UserRoles> listEntities) {
        List<UserRoles> results = null;
        try {
            if (userId != null) {
                deleteAllUserRolesByUserId(userId);
            }

            results = userRolesRepository.saveAll(listEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    /*
                                         ================== ROLE - PRIVILEGES ==================
     */
//    public List<RolePrivilegeResponse> getRolePrivilegesNew() {
//        List<Object[]> results = rolePrivilegesRepository.findRolePrivilegesNewRaw();
//        return results.stream().map(row -> new RolePrivilegeResponse(
//                (UUID) row[0],  // id
//                (UUID) row[1],  // role_id
//                (UUID) row[2], // privilege_id
//                (String) row[3], // module_name
//                (String) row[4],// name
//                (String) row[5],// access_type
//                (Boolean) row[6] // allowed
//        )).collect(Collectors.toList());
//    }

    public List<RolePrivileges> findRolePrivilegesByRoleId(UUID roleId) {
        List<RolePrivileges> results = null;
        try {
            results = rolePrivilegesRepository.findRolePrivilegesByRoleId(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    @Transactional
    @Modifying
    public void deleteAllRolePrivilegesVyRoleId(UUID roleId) {
        try {
            rolePrivilegesRepository.deleteRolePrivilegesByRoleId(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RolePrivileges> saveAllRolePrivileges(UUID roleId, List<RolePrivileges> listEntities) {
        List<RolePrivileges> results = null;
        try {
            if (roleId != null) {
                deleteAllRolePrivilegesVyRoleId(roleId);
            }

            results = rolePrivilegesRepository.saveAll(listEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    //Company Branch
    public Optional<UserBranch> getUserCompanyBranch(UUID id) {
        return userBranchRepository.findById(id);
    }

    public List<UserBranch> findUserCompanyBranchByUserId(UUID userId) {
        return userBranchRepository.findUserCompanyBranchByUserId(userId);
    }

    public Map<String, Object> getAllBranchAccessedByUserId(UUID id){

        Map<String, Object> returnData = new HashMap<>();

        try{

            //Kalo ada Data branch ID
            List<UserBranch> userBranches = findUserCompanyBranchByUserId(id);
            List<Branch> branches = userBranches.stream()
                    .map(UserBranch::getBranch)
                    .filter(Objects::nonNull).toList();

            if(userBranches.isEmpty()){
                branches = branchService.findContainer(new Branch(), null);
            }

            List<UUID> branchesUuid = userBranches.stream()
                    .map(UserBranch::getBranch)
                    .filter(Objects::nonNull)
                    .map(Branch::getId)
                    .filter(Objects::nonNull)
                    .toList();

            returnData.put("status", "success");
            returnData.put("branches", branches);
            returnData.put("branchesUuid", branchesUuid);

        }catch (Exception e){
            returnData.put("status", "InternalError");
            returnData.put("errMessage", e.getMessage());
            returnData.put("err", e);
        }

        return returnData;
    }

    public List<UserBranch> saveAllUserBranches(UUID userId, List<UserBranch> entities){
        List<UserBranch> saved = null;

        try{
            if(userId != null){
                deleteUserCompanyBranchByUserId(userId);
            }

            saved = userBranchRepository.saveAll(entities);
        }catch (Exception e){
            e.printStackTrace();
        }

        return saved;
    }

    @Transactional
    public void deleteUserCompanyBranchByUserId(UUID userId){
        userBranchRepository.deleteUserBranchById(userId);
    }
}
