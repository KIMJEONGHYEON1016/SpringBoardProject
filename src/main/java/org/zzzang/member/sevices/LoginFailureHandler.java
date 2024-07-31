package org.zzzang.member.sevices;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

        form.setSuccess(false);
        session.setAttribute("requestLogin", form);


        // 로그인 실패시 로그인 페이지 이동
        response.sendRedirect(request.getContextPath() + "/member/login");
    }
}
