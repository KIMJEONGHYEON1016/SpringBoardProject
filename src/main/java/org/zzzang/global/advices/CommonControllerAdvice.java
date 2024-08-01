package org.zzzang.global.advices;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.zzzang.member.MemberUtil;
import org.zzzang.member.entities.Member;

@RequiredArgsConstructor
@ControllerAdvice("org.zzzang")
public class CommonControllerAdvice {
    private final MemberUtil memberUtil;;

    @ModelAttribute("loggedMember")
    public Member loggedMember() {
        return memberUtil.getMember();
    }

    @ModelAttribute("isLogin")
    public boolean isLogin() {
        return memberUtil.isLogin();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        return memberUtil.isAdmin();
    }
}
