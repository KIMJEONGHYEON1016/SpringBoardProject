package org.zzzang.file.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zzzang.file.constants.FileStatus;
import org.zzzang.file.entities.FileInfo;
import org.zzzang.file.repositories.FileInfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadDoneService {
    private final FileInfoRepository repository;
    private final FileInfoService infoService;

    public void process(String gid, String location) {
        List<FileInfo> items = infoService.getList(gid, location, FileStatus.All);

        items.forEach(i -> i.setDone(true));

        repository.saveAllAndFlush(items);
    }

    public void process(String gid) {
        process(gid, null);
    }
}
