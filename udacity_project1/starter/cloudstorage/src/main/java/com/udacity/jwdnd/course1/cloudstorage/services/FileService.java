package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile fileUpload, String userName) throws IOException;

    File getFile(String fileName, String userName);

    String deleteFile(Integer fileId);
}
