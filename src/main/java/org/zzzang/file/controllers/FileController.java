package org.zzzang.file.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zzzang.file.entities.FileInfo;
import org.zzzang.file.services.FileDeleteService;
import org.zzzang.file.services.FileDownloadService;
import org.zzzang.file.services.FileInfoService;
import org.zzzang.file.services.FileUploadService;
import org.zzzang.global.exceptions.RestExceptionProcessor;
import org.zzzang.global.rests.JSONData;

import java.util.List;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController implements RestExceptionProcessor {

    private final FileUploadService uploadService;
    private final FileDownloadService downloadService;
    private final FileInfoService infoService;
    private final FileDeleteService deleteService;

    @PostMapping("/upload")
    public ResponseEntity<JSONData> upload(@RequestPart("file") MultipartFile[] files, @RequestParam(name="gid", required = false) String gid, @RequestParam(name="location", required = false) String location) {
        List<FileInfo> itmes = uploadService.upload(files, gid, location);
        HttpStatus status = HttpStatus.CREATED;
        JSONData data = new JSONData(itmes);
        data.setStatus(status);


        return ResponseEntity.status(status).body(data);
    }

    @GetMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq) {
        downloadService.download(seq);
    }

    @DeleteMapping("/delete/{seq}")
    public JSONData delete(@PathVariable("seq") Long seq) {
       FileInfo data = deleteService.delete(seq);

       return new JSONData(data);
    }

    @DeleteMapping("/deletes/{gid}")
    public JSONData deletes(@PathVariable String gid, @RequestParam(name="location", required = false) String location) {
        List<FileInfo> items = deleteService.delete(gid, location);

        return new JSONData(items);
    }

    // 개별 조회
    @GetMapping("/info/{seq}")
    public JSONData get(@PathVariable("seq") Long seq) {
        FileInfo data = infoService.get(seq);

        return new JSONData(data);
    }

    // 목록 조회
    @GetMapping("/list/{gid}")
    public JSONData get(@PathVariable("gid") String gid, @RequestParam(name="location", required = false) String location) {
        List<FileInfo> items = infoService.getList(gid, location);

        return new JSONData(items);
    }

}

