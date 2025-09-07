package com.jybeomss1.wordbattle_backend.room.adapter.out.persistence;

import com.jybeomss1.wordbattle_backend.game.domain.GameStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.jybeomss1.wordbattle_backend.room.adapter.out.persistence.QRoomJpaEntity.roomJpaEntity;
import static com.jybeomss1.wordbattle_backend.room.adapter.out.persistence.QRoomUserJpaEntity.roomUserJpaEntity;

@RequiredArgsConstructor
public class RoomJpaRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<RoomJpaEntity> findRoomsWithPaging(GameStatus gameStatus, Pageable pageable) {
        NumberExpression<Integer> statusOrder = new CaseBuilder()
                .when(roomJpaEntity.status.eq(GameStatus.WAITING)).then(0)
                .when(roomJpaEntity.status.eq(GameStatus.IN_PROGRESS)).then(1)
                .when(roomJpaEntity.status.eq(GameStatus.FINISHED)).then(2)
                .otherwise(3);

        NumberExpression<Integer> userCountOrder = new CaseBuilder()
                .when(roomJpaEntity.status.eq(GameStatus.WAITING)).then(roomJpaEntity.users.size())
                .otherwise(999);

        List<RoomJpaEntity> content = queryFactory
                .selectFrom(roomJpaEntity)
                .leftJoin(roomJpaEntity.users, roomUserJpaEntity).fetchJoin()
                .where(statusEquals(gameStatus))
                .orderBy(statusOrder.asc(), userCountOrder.asc(), roomJpaEntity.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(roomJpaEntity.count())
                .from(roomJpaEntity)
                .where(statusEquals(gameStatus))
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    private BooleanExpression statusEquals(GameStatus gameStatus) {
        return gameStatus == null || gameStatus == GameStatus.ALL ? null : roomJpaEntity.status.eq(gameStatus);
    }
} 