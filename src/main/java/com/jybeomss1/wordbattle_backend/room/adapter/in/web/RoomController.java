package com.jybeomss1.wordbattle_backend.room.adapter.in.web;

import com.jybeomss1.wordbattle_backend.common.annotation.RoomCreateSwaggerDoc;
import com.jybeomss1.wordbattle_backend.common.annotation.RoomDetailSwaggerDoc;
import com.jybeomss1.wordbattle_backend.common.annotation.RoomJoinSwaggerDoc;
import com.jybeomss1.wordbattle_backend.common.annotation.RoomListSwaggerDoc;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomCreateUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomJoinUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomListUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomDetailUseCase;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomCreateRequest;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomJoinRequest;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomListResponse;
import com.jybeomss1.wordbattle_backend.room.domain.dto.RoomDetailResponse;
import com.jybeomss1.wordbattle_backend.user.domain.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import java.util.UUID;

/**
 * 방 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomCreateUseCase roomCreateUseCase;
    private final RoomJoinUseCase roomJoinUseCase;
    private final RoomListUseCase roomListUseCase;
    private final RoomDetailUseCase roomDetailUseCase;

    /**
     * 방 생성 API
     */
    @RoomCreateSwaggerDoc
    @PostMapping("/create")
    public ResponseEntity<RoomListResponse> createRoom(
            @RequestBody RoomCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UUID userId = userDetails.getUserId();
        String name = userDetails.getUsername();
        return ResponseEntity.ok(RoomListResponse.toStringId(roomCreateUseCase.createRoom(request, userId, name)));
    }

    /**
     * 방 리스트 조회 API
     */
    @RoomListSwaggerDoc
    @GetMapping("/list")
    public ResponseEntity<List<RoomListResponse>> getRoomList(@RequestParam(value = "gameStatus", defaultValue = "WAITING") GameStatus gameStatus) {
        return ResponseEntity.ok(roomListUseCase.getRoomList(gameStatus));
    }

    /**
     * 방 참가 API
     */
    @RoomJoinSwaggerDoc
    @PostMapping("/join")
    public ResponseEntity<RoomListResponse> joinRoom(
            @RequestBody RoomJoinRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UUID userId = userDetails.getUserId();
        String name = userDetails.getUsername();
        return ResponseEntity.ok(RoomListResponse.toStringId(roomJoinUseCase.joinRoom(request, userId, name)));
    }

    /**
     * 방 상세 API
     */
    @RoomDetailSwaggerDoc
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDetailResponse> getRoomDetail(@PathVariable String roomId) {
        return ResponseEntity.ok(RoomDetailResponse.toStringId(roomDetailUseCase.getRoomDetail(UUID.fromString(roomId))));
    }
} 