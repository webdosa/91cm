package com.nineone.nocm.service;

import com.nineone.nocm.domain.ContentsFile;
import com.nineone.nocm.exception.FileStorageException;
import com.nineone.nocm.exception.UploadFileNotFoundException;
import com.nineone.nocm.repository.FileStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Autowired
    private FileStorage fileStorage;

    @Autowired
    public FileStorageService() {
        this.fileStorageLocation = Paths.get("C:/Attach").toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch (Exception ex){
            throw new FileStorageException("could not create the directory",ex);
        }
    }
    public String storeFile(MultipartFile file, ContentsFile contentsFile){
        String fileName = StringUtils.cleanPath(contentsFile.getServer_name());
        try{
            if (fileName.contains("..")){
                throw new FileStorageException("Sorry! Filename contains invalid path sequenced "+ fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }catch (IOException ex){
            throw new FileStorageException("Could not store file "+ fileName + ", please try again",ex);
        }
    }
    public Resource loadFileAsResource(String fileName){
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return  resource;
            }else{
                throw new UploadFileNotFoundException("File not found : "+fileName);
            }
        } catch (MalformedURLException ex) {
            throw new UploadFileNotFoundException("File not found : "+fileName, ex);
        }
    }
    public void DBStoreFile(ContentsFile file){
        fileStorage.saveFile(file);
    }

    public String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }


}
