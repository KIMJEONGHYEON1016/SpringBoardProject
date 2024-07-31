package org.zzzang.member.sevices;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.zzzang.member.controllers.RequestLogin;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    // 로그인 실패 시에 유입 되는 메서드
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        HttpSession session = request.getSession();

        RequestLogin form = new RequestLogin();
        form.setEmail(request.getParameter("email"));
        form.setPassword(request.getParameter("password"));

        // 아이디 또는 비밀번호가 일치하지 않는 경우
        if (exception instanceof BadCredentialsException) {
            form.setCode("BadCredentials.Login");
        } else if (exception instanceof DisabledException) {  // 탈퇴한 회원
            form.setCode("Disabled.Login");
        } else if (exception instanceof CredentialsExpiredException) {  // 비밀번호 유효기간 만료
            form.setCode("CredentialsExpired.Login");
        } else if (exception instanceof AccountExpiredException) {  // 사용자 계정 유효기간 만료
            form.setCode("AccountExpired.Login");
        } else if (exception instanceof LockedException) {  // 사용자 계정이 잠겨있는 경우
            form.setCode("Locked.Login");
        } else {
            form.setCode("Fail.Login");
        }

        form.setDefaultMessage(exception.getMessage());


        form.setSuccess(false);
        session.setAttribute("requestLogin", form);


        // 로그인 실패시 로그인 페이지 이동
        response.sendRedirect(request.getContextPath() + "/member/login");
    }
}
