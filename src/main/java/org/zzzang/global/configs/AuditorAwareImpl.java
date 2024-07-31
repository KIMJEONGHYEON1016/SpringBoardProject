package org.zzzang.global.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.zzzang.member.MemberUtil;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final MemberUtil memberUtil;

    @Override
    public Optional<String> getCurrentAuditor() {
        String email = memberUtil.isLogin() ? memberUtil.getMember().getEmail() : null;

        return Optional.ofNullable(email);
    }
}
