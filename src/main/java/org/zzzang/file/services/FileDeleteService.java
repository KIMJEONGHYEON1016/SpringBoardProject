package org.zzzang.file.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zzzang.file.entities.FileInfo;
import org.zzzang.file.repositories.FileInfoRepository;
import org.zzzang.member.MemberUtil;
import org.zzzang.member.entities.Member;

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
            throw new
        }
    }

}
