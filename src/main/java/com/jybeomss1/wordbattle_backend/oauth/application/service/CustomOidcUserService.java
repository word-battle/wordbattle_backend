package com.jybeomss1.wordbattle_backend.oauth.application.service;

import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.UserJpaEntity;
import com.jybeomss1.wordbattle_backend.user.adapter.out.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserJpaRepository userJpaRepository;
    @Override
    public OidcUser loadUser(OidcUserRequest userRequest)
            throws OAuth2AuthenticationException {
        // 1) 구글 OIDC 사용자 정보 조회
        OidcUser oidcUser = super.loadUser(userRequest);

        // 2) 이메일 기준으로 DB 조회 · 없으면 가입
        String email = oidcUser.getEmail();
        userJpaRepository.findByEmail(email)
                .orElseGet(() -> userJpaRepository.save(
                        UserJpaEntity.builder()
                                .email(email)
                                .name(oidcUser.getFullName())
                                .build()
                ));

        // 3) 새로운 권한 세팅 (예: ROLE_USER)
        return new DefaultOidcUser(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo()
        );
    }
}
