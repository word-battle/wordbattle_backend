package com.jybeomss1.wordbattle_backend.user.service;

import com.jybeomss1.wordbattle_backend.user.adapter.in.web.UserController;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserJoinUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserLoginUseCase;
import com.jybeomss1.wordbattle_backend.user.application.port.in.UserLogoutUseCase;
import com.jybeomss1.wordbattle_backend.user.domain.dto.UserJoinRequest;
import com.jybeomss1.wordbattle_backend.user.domain.dto.UserLoginRequest;
import com.jybeomss1.wordbattle_backend.user.domain.dto.response.TokenResponse;
import com.jybeomss1.wordbattle_backend.common.response.SuccessResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserController의 각 API 메서드에 대한 순수 유닛 테스트 클래스입니다.
 * 실제 서비스/DB가 아닌 Mockito로 목(mock) 객체만 사용합니다.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    // 테스트 대상 컨트롤러에 목 객체를 주입
    @InjectMocks
    private UserController userController;
    @Mock
    private UserJoinUseCase userJoinUseCase;
    @Mock
    private UserLoginUseCase userLoginUseCase;
    @Mock
    private UserLogoutUseCase userLogoutUseCase;

    /**
     * 회원가입 API 유닛 테스트
     * - userJoinUseCase.join()이 정상 호출되면 SUCCESS 응답이 반환되는지 검증
     */
    @Test
    @DisplayName("회원가입 성공")
    void join_success() {
        // given: 회원가입 요청 DTO 생성
        UserJoinRequest request = UserJoinRequest.builder()
                .email("test@email.com")
                .name("tester")
                .password("password")
                .build();
        // when: userJoinUseCase.join()은 void이므로 doNothing() 지정
        Mockito.doNothing().when(userJoinUseCase).join(ArgumentMatchers.any(UserJoinRequest.class));

        // then: 컨트롤러 호출 결과 검증
        ResponseEntity<SuccessResponse> response = userController.join(request);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("SUCCESS", response.getBody().getCode());
    }

    /**
     * 로그인 API 유닛 테스트
     * - userLoginUseCase.login()이 토큰을 반환하면, 컨트롤러도 동일하게 반환하는지 검증
     */
    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        // given: 로그인 요청 DTO 및 반환 토큰 생성
        UserLoginRequest request = UserLoginRequest.builder()
                .email("test@email.com")
                .password("password")
                .build();
        TokenResponse tokenResponse = new TokenResponse("access", "refresh");
        // when: userLoginUseCase.login()이 토큰 반환하도록 목 설정
        Mockito.when(userLoginUseCase.login(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(tokenResponse);
        // then: 컨트롤러 호출 결과 검증
        ResponseEntity<TokenResponse> response = userController.login(request);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("access", response.getBody().getAccessToken());
        assertEquals("refresh", response.getBody().getRefreshToken());
    }

    /**
     * 로그아웃 API 유닛 테스트
     * - userLogoutUseCase.logout()이 정상 호출되면 SUCCESS 응답이 반환되는지 검증
     */
    @Test
    @DisplayName("로그아웃 성공")
    void logout_success() {
        // given: 로그아웃 목 동작 지정
        Mockito.doNothing().when(userLogoutUseCase).logout(ArgumentMatchers.anyString());
        // when: userDetails는 null로 전달, 실제로 사용하지 않으므로 무방
        ResponseEntity<SuccessResponse> response = userController.logout(null, "Bearer testtoken");
        // then: 컨트롤러 호출 결과 검증
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("SUCCESS", response.getBody().getCode());
    }
} 