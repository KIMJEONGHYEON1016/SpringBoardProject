package org.zzzang.file.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zzzang.file.constants.FileStatus;
import org.zzzang.file.entities.FileInfo;
import org.zzzang.file.repositories.FileInfoRepository;
import org.zzzang.global.exceptions.UnAuthorizedException;
import org.zzzang.member.MemberUtil;
import org.zzzang.member.entities.Member;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDeleteService {
    private final FileInfoService infoService;
    private final FileInfoRepository infoRepository;
    private final MemberUtil memberUtil;

    public FileInfo delete(long seq) {
        FileInfo data = infoService.get(seq);
        String filePath = data.getFilePath();
        String createBy = data.getCreatedBy(); // 업로드한 회원 이메일

        Member member = memberUtil.getMember();
        if (!memberUtil.isAdmin() && StringUtils.hasText(createBy) && memberUtil.isLogin() && !member.getEmail().equals(createBy)) {
            throw new UnAuthorizedException();
        }

        // 파일 삭제
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        // 파일 정보 삭제
        infoRepository.delete(data);
        infoRepository.flush();

        return data;
    }

    public List<FileInfo> delete(String gid, String location, FileStatus status) {
        List<FileInfo> items = infoService.getList(gid, location, status);
        items.forEach(i -> delete(i.getSeq()));

        return items;
    }

    public List<FileInfo> delete(String gid, String location) {
        return delete(gid, location, FileStatus.All);
    }

    public List<FileInfo> delete(String gid) {
        return delete(gid, null);
    }

}
