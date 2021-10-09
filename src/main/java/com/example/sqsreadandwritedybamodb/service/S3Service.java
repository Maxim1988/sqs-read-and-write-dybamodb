package com.example.sqsreadandwritedybamodb.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.example.sqsreadandwritedybamodb.configuration.AmazonS3Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class S3Service {

    @Autowired
    public AmazonS3Configuration amazonS3Configuration;

    public void uploadFile(String bucketName, String fileName, String absoluteFilePath) {
        amazonS3Configuration.amazonS3client().putObject(bucketName, fileName, getFileByString(absoluteFilePath));
    }

    public void deleteFile(String bucketName, String fileName) {
        amazonS3Configuration.amazonS3client().deleteObject(bucketName, fileName);
    }

    public void readFile(String bucketName, String fileName) {
        AmazonS3 s3 = amazonS3Configuration.amazonS3client();
        S3Object s3Object = s3.getObject(new GetObjectRequest(bucketName, fileName));
        InputStream inputStream = s3Object.getObjectContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFile(String bucketName, String filePrefix, String folderName, boolean recurcive) {
        TransferManager transferManager = new TransferManager();
        MultipleFileUpload multipleFileUpload = transferManager.uploadDirectory(bucketName, filePrefix,
                getFileByString(folderName), recurcive);
        try {
            multipleFileUpload.waitForCompletion();
        } catch (AmazonServiceException e) {
            System.err.println("Amazon service error:" + e.getMessage());
            System.exit(1);
        } catch (AmazonClientException e) {
            System.err.println("Amazon client error:" + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("Transfer interrupted:" + e.getMessage());
            System.exit(1);
        }
        transferManager.shutdownNow();
    }

    private File getFileByString(String fileName) {
        return new File(fileName);
    }
}
