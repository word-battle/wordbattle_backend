package com.jybeomss1.wordbattle_backend.room.adapter.in.web;

import com.jybeomss1.wordbattle_backend.common.annotation.*;
import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomCreateUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomDetailUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomJoinUseCase;
import com.jybeomss1.wordbattle_backend.room.application.port.in.RoomListUseCase;
import com.jybeomss1.wordbattle_backend.room.domain.dto.*;
import com.jybeomss1.wordbattle_backend.user.domain.dto.CustomUserDetails;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomCreateUseCase roomCreateUseCase;
    private final RoomJoinUseCase roomJoinUseCase;
    private final RoomListUseCase roomListUseCase;
    private final RoomDetailUseCase roomDetailUseCase;

    @RoomCreateSwaggerDoc
    @PostMapping("/create")
    public ResponseEntity<RoomCreateResponse> createRoom(
            @RequestBody RoomCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UUID userId = userDetails.getUserId();
        String name = userDetails.getUsername();
        return ResponseEntity.ok(roomCreateUseCase.createRoom(request, userId, name));
    }

    @RoomListSwaggerDoc
    @GetMapping("/list")
    public ResponseEntity<Page<RoomListResultResponse>> getRoomList(
            @RequestParam(value = "gameStatus", defaultValue = "ALL") GameStatus gameStatus,
            Pageable pageable
    ) {
        return ResponseEntity.ok(roomListUseCase.getRoomList(gameStatus, pageable));
    }

    @RoomJoinSwaggerDoc
    @PostMapping("/join")
    public ResponseEntity<RoomJoinResponse> joinRoom(
            @RequestBody RoomJoinRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UUID userId = userDetails.getUserId();
        String name = userDetails.getUsername();
        return ResponseEntity.ok(roomJoinUseCase.joinRoom(request, userId, name));
    }

    @RoomJoinCodeSwaggerDoc
    @PostMapping("/join-code/{joinCode}")
    public ResponseEntity<RoomJoinResponse> joinRoomByJoinCode(
            @PathVariable @NotNull @NotEmpty String joinCode,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UUID userId = userDetails.getUserId();
        String name = userDetails.getUsername();
        return ResponseEntity.ok(roomJoinUseCase.joinRoomByJoinCode(joinCode, userId, name));
    }

    @RoomDetailSwaggerDoc
    @GetMapping("/detail/{roomId}")
    public ResponseEntity<RoomDetailResponse> getRoomDetail(@PathVariable String roomId) {
        return ResponseEntity.ok(RoomDetailResponse.toStringId(roomDetailUseCase.getRoomDetail(UUID.fromString(roomId))));
    }
} 