package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstants;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {

    private final UserService userService;
    private final FileMapper fileMapper;

    public FileServiceImpl(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    @Override
    public String uploadFile(MultipartFile fileUpload, String userName) throws IOException {
        Integer userId = userService.getUser(userName).getUserId();

        if (fileUpload.isEmpty()) {
            return CommonConstants.NO_FILE_UPLOAD;
        } else if (fileUpload.getSize() > CommonConstants.FILE_1_MB) {
            return CommonConstants.FILE_UPLOAD_TOO_LARGE;
        } else if (fileMapper.getFile(userId, fileUpload.getOriginalFilename()) != null) {
            return CommonConstants.FILE_EXIST;
        } else {
            File file = new File(
                    null,
                    fileUpload.getOriginalFilename(),
                    fileUpload.getContentType(),
                    Long.toString(fileUpload.getSize()),
                    userId,
                    fileUpload.getBytes());
            int res = fileMapper.insertFile(file);
            if (res < 0) {
                return CommonConstants.UPLOAD_FILE_FAIL;
            }
        }
        return null;
    }

    @Override
    public File getFile(String fileName, String userName) {
        Integer userId = userService.getUser(userName).getUserId();
        return fileMapper.getFile(userId, fileName);
    }

    @Override
    public String deleteFile(Integer fileId) {
        int result = fileMapper.deleteFile(fileId);
        if (result < 0) {
            return CommonConstants.DELETE_FILE_FAIL;
        }
        return null;
    }
}
