package com.example.sqsreadandwritedybamodb.controller;

import com.example.sqsreadandwritedybamodb.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/s3")
public class AmazonS3Controller {

    @Autowired
    private S3Service s3Service;

    @GetMapping("/upload/{bucketName}/{fileName}/{absoluteFilePath}")
    public void uploadFile(@PathVariable(value = "bucketName") String bucketName,
                           @PathVariable(value = "fileName") String fileName,
                           @PathVariable(value = "absoluteFilePath") String absoluteFilePath) {
        s3Service.uploadFile(bucketName, fileName, absoluteFilePath);
    }

    @GetMapping("/delete/{bucketName}/{fileName}")
    public void deleteFile(@PathVariable(value = "bucketName") String bucketName,
                           @PathVariable(value = "fileName") String fileName) {
        s3Service.deleteFile(bucketName, fileName);
    }

    @GetMapping("/read/{bucketName}/{fileName}")
    public void readFile(@PathVariable(value = "bucketName") String bucketName,
                         @PathVariable(value = "fileName") String fileName) {
        s3Service.readFile(bucketName, fileName);
    }

    @GetMapping("/update/{bucketName}/{folderName}")
    public void updateFile(@PathVariable(value = "bucketName") String bucketName,
                           @PathVariable(value = "folderName") String folderName) {
        s3Service.updateFile(bucketName, "ss", folderName, false);
    }
}
