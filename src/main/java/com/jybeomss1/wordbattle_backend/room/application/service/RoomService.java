package com.jybeomss1.wordbattle_backend.room.application.service;

import com.jybeomss1.wordbattle_backend.common.exceptions.BaseException;
import com.jybeomss1.wordbattle_backend.common.exceptions.ErrorCode;
import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.jybeomss1.wordbattle_backend.room.application.port.in.*;
import com.jybeomss1.wordbattle_backend.room.application.port.out.RoomPort;
import com.jybeomss1.wordbattle_backend.room.domain.Room;
import com.jybeomss1.wordbattle_backend.room.domain.RoomUser;
import com.jybeomss1.wordbattle_backend.room.domain.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService implements RoomCreateUseCase, RoomJoinUseCase, RoomListUseCase, RoomDetailUseCase, RoomExitUseCase {
    private final RoomPort roomPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RoomCreateResponse createRoom(RoomCreateRequest request, UUID userId, String name) {

        RoomUser hostUser = RoomUser.fromHostInfo(userId.toString(), name);
        String encodedPassword = null;
        if (request.getPassword() != null) {
            encodedPassword = passwordEncoder.encode(request.getPassword());
        }

        Room room = request.toEntity(hostUser, encodedPassword);

        Room saved = roomPort.save(room);

        return RoomCreateResponse.from(saved);
    }

    @Override
    @Transactional
    public RoomJoinResponse joinRoom(RoomJoinRequest request, UUID userId, String name) {

        Room room = roomPort.findById(request.getRoomId())
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));

        if (room.getUsers() != null && room.getUsers().size() >= 10) {
            throw new BaseException(ErrorCode.ROOM_FULL);
        }

        if (room.isHasPassword()) {
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                throw new BaseException(ErrorCode.ROOM_PASSWORD_REQUIRED);
            }
            if (!passwordEncoder.matches(request.getPassword(), room.getPassword())) {
                throw new BaseException(ErrorCode.ROOM_PASSWORD_MISMATCH);
            }
        }

        RoomUser joinUser = RoomUser.fromJoinInfo(userId.toString(), name);

        Room updatedRoom = Room.fromJoin(room, joinUser);

        roomPort.save(updatedRoom);

        return RoomJoinResponse.from(updatedRoom);
    }


    @Override
    @Transactional
    public RoomJoinResponse joinRoomByJoinCode(String joinCode, UUID userId, String name) {
        Room room = roomPort.findByJoinCode(joinCode)
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));

        if (room.getUsers() != null && room.getUsers().size() >= 10) {
            throw new BaseException(ErrorCode.ROOM_FULL);
        }

        RoomUser joinUser = RoomUser.fromJoinInfo(userId.toString(), name);

        Room updatedRoom = Room.fromJoin(room, joinUser);

        roomPort.save(updatedRoom);

        return RoomJoinResponse.from(updatedRoom);
    }

    /**
     * 방 리스트 조회 (성능 최적화 + 페이징)
     */
    @Override
    public Page<RoomListResultResponse> getRoomList(GameStatus gameStatus, Pageable pageable) {
        Page<Room> rooms = roomPort.findRooms(gameStatus, pageable);
        return rooms.map(RoomListResultResponse::fromRoom);
    }

    @Override
    public RoomDetailResponse getRoomDetail(UUID roomId) {
        Room room = roomPort.findById(roomId)
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));
        return RoomDetailResponse.from(room);
    }

    @Override
    @Transactional
    public void exit(UUID roomId, UUID userId) {
        Room room = roomPort.findById(roomId)
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));
        List<RoomUser> users = room.getUsers();
        boolean removed = users.removeIf(roomUser -> roomUser.getUserId().equals(userId));

        if (!removed) {
            throw new BaseException(ErrorCode.USER_NOT_FOUND_IN_ROOM);
        }

        roomPort.save(room);
    }
}