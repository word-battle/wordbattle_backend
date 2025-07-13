package com.jybeomss1.wordbattle_backend.room.service;

import com.jybeomss1.wordbattle_backend.room.adapter.in.web.RoomController;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomCreateUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomJoinUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomListUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomDetailUseCase;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomCreateRequest;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomDetailResponse;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomCreateResponse;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomJoinResponse;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomListResultResponse;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomJoinRequest;
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
                .roundCount(5)
                .build();
        CustomUserDetails userDetails = Mockito.mock(CustomUserDetails.class);
        Mockito.when(userDetails.getUserId()).thenReturn(UUID.randomUUID());
        Mockito.when(userDetails.getUsername()).thenReturn("tester");
        RoomCreateResponse responseDto = RoomCreateResponse.builder().roomId(UUID.randomUUID().toString()).roomName("testRoom").roundCount(5).hasPassword(false).joinCode("ABC123").build();
        Mockito.when(roomCreateUseCase.createRoom(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(responseDto);
        ResponseEntity<RoomCreateResponse> response = roomController.createRoom(request, userDetails);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("testRoom", response.getBody().getRoomName());
        assertEquals("ABC123", response.getBody().getJoinCode());
    }

    @Test
    @DisplayName("방 리스트 조회 성공")
    void getRoomList_success() {
        RoomListResultResponse.RoomListItem room1 = RoomListResultResponse.RoomListItem.builder().roomId(UUID.randomUUID().toString()).roomName("room1").roundCount(5).hasPassword(false).currentUsers(1).build();
        RoomListResultResponse listResponse = RoomListResultResponse.builder().rooms(List.of(room1)).build();
        Mockito.when(roomListUseCase.getRoomList(ArgumentMatchers.any())).thenReturn(listResponse);
        ResponseEntity<RoomListResultResponse> response = roomController.getRoomList(null);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getRooms().isEmpty());
        assertEquals("room1", response.getBody().getRooms().get(0).getRoomName());
    }

    @Test
    @DisplayName("방 상세 조회 성공")
    void getRoomDetail_success() {
        String roomId = UUID.randomUUID().toString();
        RoomDetailResponse detail = RoomDetailResponse.builder().roomId(roomId).roomName("room1").roundCount(5).hasPassword(false).build();
        Mockito.when(roomDetailUseCase.getRoomDetail(ArgumentMatchers.any())).thenReturn(detail);
        ResponseEntity<RoomDetailResponse> response = roomController.getRoomDetail(roomId);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(roomId, response.getBody().getRoomId());
    }

    @Test
    @DisplayName("방 참가 성공")
    void joinRoom_success() {
        RoomJoinResponse joinResponse = RoomJoinResponse.builder().roomId(UUID.randomUUID().toString()).roomName("testRoom").roundCount(5).hasPassword(false).currentUsers(2).build();
        RoomJoinRequest joinRequest = RoomJoinRequest.builder().roomId(UUID.randomUUID()).password("").build();
        CustomUserDetails userDetails = Mockito.mock(CustomUserDetails.class);
        Mockito.when(userDetails.getUserId()).thenReturn(UUID.randomUUID());
        Mockito.when(userDetails.getUsername()).thenReturn("tester");
        Mockito.when(roomJoinUseCase.joinRoom(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(joinResponse);
        ResponseEntity<RoomJoinResponse> response = roomController.joinRoom(joinRequest, userDetails);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("testRoom", response.getBody().getRoomName());
        assertEquals(2, response.getBody().getCurrentUsers());
    }
} 