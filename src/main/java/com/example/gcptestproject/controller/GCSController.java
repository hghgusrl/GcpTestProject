package com.example.gcptestproject.controller;

import com.example.gcptestproject.dto.DownloadReqDto;
import com.example.gcptestproject.dto.UploadReqDto;
import com.example.gcptestproject.service.GCSService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("test")
@RestController
public class GCSController {

    @Autowired
    GCSService gcsService;

    @PostMapping("/gcs/download")
    public ResponseEntity localDownloadFromStorage(@RequestBody DownloadReqDto downloadReqDto) throws IOException {
        Blob fileFromGCS = gcsService.downloadFileFromGCS(downloadReqDto);
        return ResponseEntity.ok(fileFromGCS.toString());
    }

    @PostMapping("/gcs/upload")
    public ResponseEntity localUploadFromStorage(@RequestBody UploadReqDto uploadReqDto) throws IOException {
        BlobInfo fileFromGCS = gcsService.uploadFileFromGCS(uploadReqDto);
        return ResponseEntity.ok(fileFromGCS.toString());
    }
}
