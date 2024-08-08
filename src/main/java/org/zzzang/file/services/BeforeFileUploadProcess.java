package org.zzzang.file.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zzzang.file.controllers.RequestUpload;

@Service
public class BeforeFileUploadProcess {
    /**
     * 파일 업로드 전 처리
     *
     * @param form
     */
    private void process(RequestUpload form) {
        // 업로드 된 파일에서 이미지만 포함되어 있는지 체크
       if (form.isImageOnly()) {
           for (MultipartFile file : form.getFiles()) {
               String contentType = file.getContentType();
               // 이미지이면 image/png, image/gif ..
               // 이미지가 아닌 파일이 포함된 경우
               if (!contentType.contains("image/")) {

               }
           }
       }
    }
}
