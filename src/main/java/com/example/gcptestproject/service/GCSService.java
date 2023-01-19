package com.example.gcptestproject.service;


import com.example.gcptestproject.dto.DownloadReqDto;
import com.example.gcptestproject.dto.UploadReqDto;
import com.google.api.client.http.GenericUrl;
import com.google.api.services.storage.model.StorageObject;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class GCSService {

    @Value("${spring.cloud.gcp.credentials.location}")
    String PATH_TO_JSON_KEY;

    @Autowired
    ResourceLoader resourceLoader;

    public Blob downloadFileFromGCS(DownloadReqDto downloadReqDto) throws IOException {

        File file = new File("key file");

        Storage storage = StorageOptions.newBuilder()
                .setProjectId("project id")
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(file)))
                .build()
                .getService();
        Blob blob = storage.get(downloadReqDto.getBucketName(), downloadReqDto.getDownloadFileName());

        ReadChannel readChannel = blob.reader();

        Path path = Paths.get(downloadReqDto.getLocalFileLocation());
        blob.downloadTo(path);
        return blob;
    }

    @SuppressWarnings("deprecation")
    public BlobInfo uploadFileFromGCS(UploadReqDto uploadReqDto) throws IOException {

        File file = new File("key file");

        StorageOptions options = StorageOptions.newBuilder()
                .setProjectId("project id")
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(file)))
                .build();

        Storage storage = options.getService();

        BlobInfo blobInfo = BlobInfo.newBuilder(uploadReqDto.getBucketName(), uploadReqDto.getUploadFileName())
                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
                        .build();

        Blob blob = storage.create(blobInfo, new FileInputStream(uploadReqDto.getLocalFileLocation()));

        return blob;
    }
}