package org.zzzang.member.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.zzzang.member.controllers.RequestJoin;

@Component
public class JoinValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestJoin.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        /**
         * 1. 이미 가입된 회원인지 체크
         * 2. 비밀번호, 비밀번호 확인 일치 여부
         * 3. 비밀번호 복잡성 체크
         * 4. 전화번호 형식 체크
         */
    }
}
