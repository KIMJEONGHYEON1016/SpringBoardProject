package org.zzzang.file.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zzzang.file.entities.FileInfo;
import org.zzzang.file.services.FileUploadService;
import org.zzzang.global.exceptions.RestExceptionProcessor;
import org.zzzang.global.rests.JSONData;

import java.util.List;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController implements RestExceptionProcessor {

    private final FileUploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<JSONData> upload(@RequestPart("file") MultipartFile[] files, @RequestParam(name="gid", required = false) String gid, @RequestParam(name="location", required = false) String location) {
        List<FileInfo> itmes = uploadService.upload(files, gid, location);
        HttpStatus status = HttpStatus.CREATED;
        JSONData data = new JSONData(itmes);
        data.setStatus(status);


        return ResponseEntity.status(status).body(data);
    }
}

