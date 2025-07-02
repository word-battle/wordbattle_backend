package com.jybeomss1.wordbattle_backend.room.service;

import com.jybeomss1.wordbattle_backend.room.adapter.in.web.RoomController;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomCreateUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomJoinUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomListUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomDetailUseCase;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomCreateRequest;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomListResponse;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomDetailResponse;
import com.jybeomss1.wordbattle_backend.common.response.SuccessResponse;
import com.jybeomss1.wordbattle_backend.user.domain.dto.CustomUserDetails;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    @InjectMocks
    private RoomController roomController;
    @Mock
    private RoomCreateUseCase roomCreateUseCase;
    @Mock
    private RoomJoinUseCase roomJoinUseCase;
    @Mock
    private RoomListUseCase roomListUseCase;
    @Mock
    private RoomDetailUseCase roomDetailUseCase;

    @Test
    @DisplayName("방 생성 성공")
    void createRoom_success() {
        RoomCreateRequest request = RoomCreateRequest.builder()
                .roomName("testRoom")
                .password("")
                .quizCount(5)
                .userId(UUID.randomUUID())
                .build();
        CustomUserDetails userDetails = Mockito.mock(CustomUserDetails.class);
        Mockito.when(userDetails.getUserId()).thenReturn(UUID.randomUUID());
        Mockito.when(userDetails.getUsername()).thenReturn("tester");
        RoomListResponse responseDto = RoomListResponse.builder().roomId(UUID.randomUUID().toString()).roomName("testRoom").quizCount(5).hasPassword(false).build();
        Mockito.when(roomCreateUseCase.createRoom(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(responseDto);
        ResponseEntity<RoomListResponse> response = roomController.createRoom(request, userDetails);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("testRoom", response.getBody().getRoomName());
    }

    @Test
    @DisplayName("방 리스트 조회 성공")
    void getRoomList_success() {
        RoomListResponse room1 = RoomListResponse.builder().roomId(UUID.randomUUID().toString()).roomName("room1").quizCount(5).hasPassword(false).build();
        Mockito.when(roomListUseCase.getRoomList(ArgumentMatchers.any())).thenReturn(List.of(room1));
        ResponseEntity<List<RoomListResponse>> response = roomController.getRoomList(null);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    @DisplayName("방 상세 조회 성공")
    void getRoomDetail_success() {
        String roomId = UUID.randomUUID().toString();
        RoomDetailResponse detail = RoomDetailResponse.builder().roomId(roomId).roomName("room1").gameCount(5).hasPassword(false).build();
        Mockito.when(roomDetailUseCase.getRoomDetail(ArgumentMatchers.any())).thenReturn(detail);
        ResponseEntity<RoomDetailResponse> response = roomController.getRoomDetail(roomId);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(roomId, response.getBody().getRoomId());
    }
} 