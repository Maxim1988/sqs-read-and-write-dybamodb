package com.example.sqsreadandwritedybamodb.controller;

import com.example.sqsreadandwritedybamodb.service.S3Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AmazonS3Controller.class)
public class AmazonS3ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private S3Service s3Service;

    @Test
    public void uploadFileTest() throws Exception {
        doNothing().when(s3Service).uploadFile(any(String.class), any(String.class), any(String.class));
        mockMvc.perform(get("/s3/upload/{bucketName}/{fileName}/{absoluteFilePath}",
                        "bucketName", "fileName", "absoluteFilePath")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFileTest() throws Exception {
        doNothing().when(s3Service).deleteFile(any(String.class), any(String.class));
        mockMvc.perform(get("/s3/delete/{bucketName}/{fileName}",
                        "bucketName", "fileName")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void readFileTest() throws Exception {
        doNothing().when(s3Service).readFile(any(String.class), any(String.class));
        mockMvc.perform(get("/s3/read/{bucketName}/{fileName}",
                        "bucketName", "fileName")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateFileTest() throws Exception {
        doNothing().when(s3Service).updateFile(any(String.class), any(String.class),
                any(String.class), any(boolean.class));
        mockMvc.perform(get("/s3/update/{bucketName}/{fileName}",
                        "bucketName", "fileName")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
