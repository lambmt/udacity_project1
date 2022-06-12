package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Principal principal, Model model) throws IOException {
        String userName = principal.getName();
        String error = fileService.uploadFile(fileUpload, userName);

        if (error != null) {
            model.addAttribute("error", error);
        } else {
            model.addAttribute("success", true);
        }
        return "res";
    }

    @GetMapping("/file")
    public ResponseEntity<?> download(@RequestParam String fileName, Principal principal) {
        String userName = principal.getName();
        File file = fileService.getFile(fileName, userName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFileData());
    }

    @GetMapping("/file/delete")
    public String deleteFile(@RequestParam Integer fileId, Model model) {
        String error = fileService.deleteFile(fileId);

        if (error != null) {
            model.addAttribute("error", error);
        } else {
            model.addAttribute("success", true);
        }
        return "res";
    }

}
