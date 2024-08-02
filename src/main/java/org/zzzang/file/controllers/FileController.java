package org.zzzang.file.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/upload")
    public void upload(@RequestPart("file") MultipartFile[] files, @RequestParam(name="gid", required = false) String gid, @RequestParam(name="location", required = false) String location) {
    }
}

