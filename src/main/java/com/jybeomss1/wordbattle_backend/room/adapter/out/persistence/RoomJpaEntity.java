package com.jybeomss1.wordbattle_backend.room.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.common.util.BaseTimeEntity;
import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.jybeomss1.wordbattle_backend.room.domain.Room;
import com.jybeomss1.wordbattle_backend.room.domain.RoomUser;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "room")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RoomJpaEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String password;

    @Column(nullable = false)
    private int roundCount;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @Column(nullable = false)
    private boolean hasPassword;

    @Column(nullable = false, unique = true)
    private String joinCode;

    @Builder.Default
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomUserJpaEntity> users = new ArrayList<>();

    @Column(nullable = false)
    private UUID hostUserId;

    public static RoomJpaEntity fromDomain(Room room) {
        return RoomJpaEntity.builder()
                .id(room.getId())
                .name(room.getName())
                .password(room.getPassword())
                .roundCount(room.getRoundCount())
                .status(room.getStatus())
                .users(room.getUsers().stream().map(user ->
                        RoomUserJpaEntity.builder()
                                .userId(user.getUserId())
                                .score(user.getScore())
                                .build()
                ).collect(java.util.stream.Collectors.toList()))
                .hasPassword(room.isHasPassword())
                .hostUserId(room.getHostUserId())
                .joinCode(room.getJoinCode())
                .build();
    }

    public Room toDomain() {
        return Room.builder()
                .id(this.getId())
                .name(this.getName())
                .password(this.getPassword())
                .roundCount(this.getRoundCount())
                .status(this.getStatus())
                .users(this.getUsers().stream().map(u ->
                        RoomUser.builder()
                                .userId(u.getUserId())
                                .score(u.getScore())
                                .build()
                ).collect(java.util.stream.Collectors.toList()))
                .hasPassword(this.isHasPassword())
                .hostUserId(this.getHostUserId())
                .joinCode(this.getJoinCode())
                .build();
    }
} 