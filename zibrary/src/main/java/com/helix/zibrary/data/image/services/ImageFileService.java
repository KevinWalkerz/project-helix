package com.helix.zibrary.data.image.services;

import com.helix.zibrary.data.image.repositories.ImageFileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Service
public class ImageFileService {

//    Setup setup;
//
//    PropertiesData propertiesData;

    private final ImageFileRepository imageFileRepository;


//    @Autowired
//    private OssUrlCache ossUrlCache;

    public ImageFileService(ImageFileRepository imageFileRepository
//                            SetupService setupService,
//                            PropertiesData propertiesData
    ){
        this.imageFileRepository = imageFileRepository;
    }
//
//    //Get Data from Database ====================================================================
//    public Optional<ImageFile> getImage(UUID id){
//        Optional<ImageFile> resultOptional = null;
//        try{
//            resultOptional = imageFileRepository.findById(id);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return resultOptional;
//    }
//
//    public ImageFile getImageById(UUID id){
//        ImageFile result = getImage(id).get();
//
//        return result;
//    }
//
//    //CURD Image Data to Database =================================================================
//    //Database
//    public String getLatestImageName(String fileType){
//        String tempKode = "IMG_" + fileType + "_";
//
//        LocalDate serverDate = LocalDate.now();
//
//        String periodYear = String.valueOf(serverDate.getYear());
//        String periodYearCode = periodYear.substring(periodYear.length() - 2);
//
//        String periodMonth = String.valueOf(serverDate.getMonthValue());
//        String periodMonthCode= "";
//        if(serverDate.getMonthValue() < 10){
//            periodMonthCode = "0" + periodMonth;
//        }else{
//            periodMonthCode = periodMonth;
//        }
//
//        tempKode = tempKode + periodYearCode + periodMonthCode;
//
//        List<ImageFile> list =  imageFileRepository.findAll(ImageFileCriteria.getLatestName(tempKode), Sort.by("fileName").descending());
//
//        String id = "";
//        if(list.size() >0){
//            id = list.get(0).getFileName();
//        }
//
//        String lastNumber = "";
//        if (list.size() < 1)
//            lastNumber = "";
//        else
//            lastNumber = removeFileExtension(id);
//
//        return AutoID.generate(tempKode, lastNumber, AutoID.DEFAULT_TRANSACTION_LENGTH);
//    }
//
//    private String getFileExtension(String fileName) {
//        int dotIndex = fileName.lastIndexOf('.');
//        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
//            return fileName.substring(dotIndex);
//        }
//        return "";
//    }
//
//    public static String removeFileExtension(String fileName) {
//        int dotIndex = fileName.lastIndexOf('.');
//        if (dotIndex > 0 && dotIndex < fileName.length()) {
//            return fileName.substring(0, dotIndex);
//        }
//        return fileName;
//    }
//
//    public Map<String, Object> saveImage(MultipartFile file, EnumImageType fileType, String userName){
//
//        Map<String, Object> returnSave = new HashMap<>();
//
//        String fileName = null;
//        String filePath = null;
//        String imageUrl = null;
//        ImageFile imageFile = new ImageFile();
//
//        Map<String, Object> savedImage = saveImageFileToOssCloud(file, fileType);
//
//        if (savedImage.containsKey("error")) {
//            return savedImage;
//        }
//
//        fileName = (String) savedImage.get("name");
//        filePath = (String) savedImage.get("path");
//        imageUrl = (String) savedImage.get("imageUrl");
//
//        imageFile.setFileName(fileName);
//        imageFile.setFilePath(filePath);
//        imageFile.setStoreType(EnumImageStoreType.CLOUD_STORAGE);
//
//        imageFile.setFileSize((int) file.getSize());
//        imageFile.setCreatedBy(userName);
//        imageFile.setUpdatedBy(userName);
//
//        ImageFile saved = imageFileRepository.save(imageFile);
//
//        returnSave.put("id", saved.getId());
//        returnSave.put("fileName", fileName);
//        returnSave.put("filePath", filePath);
//        returnSave.put("imageUrl", imageUrl);
//
//        return returnSave;
//    }
//
//    @Transactional
//    @Modifying
//    public void deleteImageById(UUID id){
//        try{
//            Optional<ImageFile> optionalImageFile = getImage(id);
//            if(optionalImageFile.isPresent()){
//                if(optionalImageFile.get().getStoreType().equals(EnumImageStoreType.LOCAL_STORAGE)){
//                    File file = new File(optionalImageFile.get().getFilePath());
//
//                    if (file.exists()) {
//                        file.delete();
//                    }
//                }else{
//                    deleteImageFromOss(optionalImageFile.get().getFilePath());
//                }
//            }
//
//            imageFileRepository.deleteById(id);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    //Multipart - For Local File ==========================================================================
////Multipart - For Local File ==========================================================================
////    public Map<String, String> storeImageToLocalFile(MultipartFile file, String fileType) throws Exception {
////        Map<String, String> result = new HashMap<>();
////
////        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
////        String fileExtension = getFileExtension(originalFileName);
////        String fileName = getLatestImageName(fileType) + fileExtension;
////
////        try {
////            if (fileName.contains("..")) {
////                throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
////            }
////
////            Path fileStorageLocation;
////            if (setup.getOsType().equals(EnumOSType.WINDOWS)) {
////                fileStorageLocation = Paths.get(setup.getImageUploadPath()).toAbsolutePath().normalize();
////            } else {
////                fileStorageLocation = Paths.get(setup.getImageUploadPathLinux()).toAbsolutePath().normalize();
////            }
////
////            Path targetLocation = fileStorageLocation.resolve(fileName);
////            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
////
////            result.put("name", fileName);
////            result.put("path", targetLocation.toString());
////
////
////            return result;
////        } catch (Exception ex) {
////            result.put("error", "Could not store file " + fileName + ". " + ex);
////            return result;
////        }
////    }
////
////
////    public Resource loadLocalFileAsResource(ImageFile imageFile) throws Exception {
////        try {
////            Path fileStorageLocation = Paths.get(imageFile.getFilePath()).toAbsolutePath().normalize();
////
////            Resource resource = new UrlResource(fileStorageLocation.toUri());
////            if(resource.exists()) {
////                return resource;
////            } else {
////                throw new Exception("File not found " + imageFile.getFileName());
////            }
////        } catch (Exception ex) {
////            throw new Exception("File not found " + imageFile.getFileName(), ex);
////        }
////    }
//
//    // OSS Service - For Cloud Upload ===================================================================
//    public Map<String, Object> saveImageFileToOssCloud(MultipartFile file, EnumImageType fileType){
//        OSS ossClient = new OSSClientBuilder().build(propertiesData.getOssEndpointUrl(), propertiesData.getOssAccessKeyId(), propertiesData.getOssAccessKeySecret());
//
//        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
//        String fileExtension = getFileExtension(originalFileName);
//
//        String fileName = getLatestImageName(fileType.getAcronym()) + fileExtension;
//        String filePath = "images/" + fileType.getTypeName() + "/" + fileName;
//
//        Map<String, Object> returnData = new HashMap<>();
//
//        try (InputStream inputStream = file.getInputStream()) {
//            PutObjectRequest putObjectRequest = new PutObjectRequest(setup.getCloudBucketName(), filePath, inputStream);
//            ossClient.putObject(putObjectRequest);
//
//            returnData.put("name", fileName);
//            returnData.put("path", filePath);
//            returnData.put("imageUrl", generatePresignedUrl(filePath));
//        } catch (Exception e) {
//            returnData.put("error", "Unexpected error: " + e.getMessage());
//            returnData.put("errorException", e);
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//
//        return returnData;
//    }
//
//    public String generatePresignedUrl(String filePath) {
//        //check Cache Link first
//        String cachedUrl = ossUrlCache.get(filePath);
//        if (cachedUrl != null) {
//            return cachedUrl;
//        }
//
//        OSS ossClient = new OSSClientBuilder().build(propertiesData.getOssEndpointUrl(), propertiesData.getOssAccessKeyId(), propertiesData.getOssAccessKeySecret());
//        try {
//            // Set expiration time
//            long ttlMillis = 120 * 60 * 1000; // 2 Hours
//            Date expiration = new Date(System.currentTimeMillis() + ttlMillis);
//
//            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(setup.getCloudBucketName(), filePath);
//            request.setExpiration(expiration);
//            URL url = ossClient.generatePresignedUrl(request);
//            String finalUrl = url.toString();
//            // Save to cache
//            ossUrlCache.put(filePath, finalUrl, ttlMillis);
//
//            return finalUrl;
//        } finally {
//            ossClient.shutdown();
//        }
//    }
//
//    public Map<String, String> deleteImageFromOss(String filePath){
//        OSS ossClient = null;
//
//        Map<String, String> response = new HashMap<>();
//        try {
//            ossClient = new OSSClientBuilder().build(propertiesData.getOssEndpointUrl(), propertiesData.getOssAccessKeyId(), propertiesData.getOssAccessKeySecret());
//            ossClient.deleteObject(setup.getCloudBucketName(), filePath);
//
//            response.put("status", "Success");
//            response.put("message", "The object deletes successfully");
//        }catch(Exception e){
//            response.put("status", "error");
//            response.put("message", "Error deleting object, " + e.getMessage());
//        }
//
//        return response;
//    }
}
