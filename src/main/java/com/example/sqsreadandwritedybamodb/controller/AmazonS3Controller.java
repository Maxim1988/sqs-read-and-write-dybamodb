package com.example.sqsreadandwritedybamodb.controller;

import com.example.sqsreadandwritedybamodb.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/s3")
public class AmazonS3Controller {

    private static final String BUCKET_NAME = "s3zvyaginbucket";
    private static final String FILE_NAME = "testFile.pages";

    @Autowired
    S3Service s3Service;

    @GetMapping("/upload")
    public void uploadFile() {
        File file = new File("/Users/maximzvyagin/Downloads/work_in_jaxel/s3TestFile.pages");

        s3Service.uploadFile(BUCKET_NAME, FILE_NAME, file);
    }

    @GetMapping("/delete")
    public void deleteFile() {
        s3Service.deleteFile(BUCKET_NAME, FILE_NAME);
    }

    @GetMapping("/reed")
    public void readFile() {
        s3Service.readFile(BUCKET_NAME, FILE_NAME);
    }

    @GetMapping("/update")
    public void updateFile() {
        File file = new File("/Users/maximzvyagin/Downloads/work_in_jaxel");
        s3Service.updateFile(BUCKET_NAME, "ss", file, false);

    }
}
