package org.zzzang.member.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.zzzang.global.exceptions.ExceptionProcessor;
import org.zzzang.member.sevices.MemberSaveService;
import org.zzzang.member.validators.JoinValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@SessionAttributes("requestLogin")
// 컨트롤러가 반환하는 모든 모델에서 "requestLogin" 속성이 발견되면 이를 세션에 저장
public class MemberController implements ExceptionProcessor {

    private final JoinValidator joinValidator;
    private final MemberSaveService memberSaveService;

    //세션에 이미 requestLogin 속성이 존재하면, 세션에서 해당 객체를 가져오고, 그렇지 않으면 새로 생성된 객체를 세션에 저장
    @ModelAttribute
    public RequestLogin requestLogin() {
        return new RequestLogin();
    }

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model) {
//        boolean result = false;
//        if (!result) {
//            throw new AlertRedirectException("테스트 예외", "/mypage", HttpStatus.BAD_REQUEST);
//        }
        commonProcess("join", model);

        return "front/member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model) {
        commonProcess("join", model);

        joinValidator.validate(form, errors);

        if (errors.hasErrors()) {
            return "front/member/join";
        }

        memberSaveService.save(form); // 회원 가입 처리

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(@Valid @ModelAttribute RequestLogin form, Errors errors, Model model) {
        commonProcess("login", model);

        String code = form.getCode();
        if (StringUtils.hasText(code)) {
            errors.reject(code, form.getDefaultMessage());

            // 비번 만료인 경우 비번 재설정 페이지 이동
            if (code.equals("CredentialsExpired.Login")) {
                return "redirect:/member/password/reset";
            }
        }

        return "front/member/login";
    }

    @ResponseBody
    @GetMapping("/test1")
//    @PreAuthorize("isAuthenticated()")
    public void test1() {
        log.info("test1 - 회원만 접근 가능");
    }

    @ResponseBody
    @GetMapping("/test2")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    private void test2() {
        log.info("test2 - 관리자만 접근 가능");
    }


    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "join");

        List<String> addCss = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        addCss.add("member/style"); // 회원 공통 스타일
        if (mode.equals("join")) {
            addCommonScript.add("fileManager");
            addCss.add("member/join");
            addScript.add("member/join");
        } else if (mode.equals("login")) {
            addCss.add("member/login");
        }

        model.addAttribute("addCss", addCss);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);

    }
}