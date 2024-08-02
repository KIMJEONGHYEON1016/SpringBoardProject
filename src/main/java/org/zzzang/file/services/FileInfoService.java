package org.zzzang.file.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.zzzang.file.constants.FileStatus;
import org.zzzang.file.entities.FileInfo;
import org.zzzang.file.exceptions.FileNotFoundException;
import org.zzzang.file.repositories.FileInfoRepository;
import org.zzzang.global.configs.FileProperties;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileInfoService {

    private final FileInfoRepository infoRepository;
    private final FileProperties properties;
    private final HttpServletRequest request;

    /**
     * 파일 1개조회
     * @param seq
     * @return
     */
    public FileInfo get(Long seq){
        FileInfo item = infoRepository.findById(seq).orElseThrow(FileNotFoundException::new);

        /**
         * 2차 가공
         * 1. 파일을 접근할 수 있는 URL - 보여주기 위한 목적
         * 2. 파일을 접근할 수 있는 PATH - 파일 삭제, 다운로드 등등
         */
        return item;
    }

    /**
     * 파일여러개조회
     * @param gid
     * @param location
     * @param status : ALL:완료 + 미완료, DONE - 완료, UNDONE - 미완료
     * @return
     */
    public List<FileInfo> getList(String gid, String location, FileStatus status){


        return getList(gid, location, status);
    }


    /**
     * 파일 정보 추가 처리 : fileUrl, filePath
     * @param item
     */
    public void addFileInfo(FileInfo item){
        String fileUrl = getFileUrl(item);
        String filePath = getFilePath(item);

        item.setFileUrl(fileUrl);
        item.setFilePath(filePath);
    }

    // 브라우저 접근 주소
    public String getFileUrl(FileInfo item){
        return request.getContextPath() + properties.getUrl() + "/" + getFolder(item.getSeq()) + "/" + getFileName(item);
    }

    // 서버 업로드 경로
    public String getFilePath(FileInfo item){
        return properties.getPath() + "/" + getFolder(item.getSeq()) + "/" + getFileName(item);
    }

    public String getFolder(long seq){
        return String.valueOf(seq % 10L);
    }

    public String getFileName(FileInfo item){
        String fileName = item.getSeq() + Objects.requireNonNullElse(item.getExtension(), "");
        return fileName;
    }
}

