package org.zzzang.file.services;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.zzzang.file.entities.FileInfo;
import org.zzzang.file.repositories.FileInfoRepository;
import org.zzzang.global.configs.FileProperties;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileUploadService {

    private final FileInfoRepository fileInfoRepository;
    private final FileInfoService fileInfoService;
    private final FileProperties properties;

    public List<FileInfo> upload(MultipartFile[] files, String gid, String location) {
        /**
         * 1. 파일 정보 저장
         * 2. 파일을 서버로 이동
         * 3. 이미지이면 썸네일 생성
         * 4. 업로드한 파일 목록 반환
         */

        //gid 설정
        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();

        // 업로드 할 파일 빈 목록 생성
        List<FileInfo> uploadedFiles = new ArrayList<>();

        // 1. 파일 정보 저장
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();   // 업로드 파일 원래 이름
            String contentType = file.getContentType();   // 파일 형식 (image/jpeg)
            String extension = fileName.substring(fileName.lastIndexOf("."));   // 확장자

            FileInfo fileInfo = FileInfo.builder()
                    .gid(gid)
                    .location(location)
                    .fileName(fileName)
                    .contentType(contentType)
                    .extension(extension)
                    .build();

            fileInfoRepository.saveAndFlush(fileInfo);

            // 2. 파일을 서버로 이동
            long seq = fileInfo.getSeq();
            String uploadDir = properties.getPath() + "/" + (seq % 10L);    // 디렉토리 분산
            File dir =  new File(uploadDir);    // 경로와 파일 사이 연결을 표현
            if (!dir.exists() || !dir.isDirectory()) {
                dir.mkdir();
            }

            String uploadPath = uploadDir + "/" + seq + extension;
            try {                                       // transferTo 메서드는 파일을 지정된 경로로 이동, 복사
                file.transferTo(new File(uploadPath));  // 업로드된 파일을 uploadPath 경로에 저장
                uploadedFiles.add(fileInfo);    // 업로드 성공 파일 정보
            } catch (IOException e) {
                e.printStackTrace();
                // 파일 이동 실패시 정보 삭제
                fileInfoRepository.delete(fileInfo);
                fileInfoRepository.flush();
            }
        }

        uploadedFiles.forEach(fileInfoService::addFileInfo);

        return uploadedFiles;
    }
}

