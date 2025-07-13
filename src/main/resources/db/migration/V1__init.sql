-- user 테이블
CREATE TABLE "user" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    social_type VARCHAR(255),
    social_id VARCHAR(255),
    deleted BOOLEAN NOT NULL,
    created_date TIMESTAMP NOT NULL,
    modified_date TIMESTAMP
);

-- room 테이블
CREATE TABLE room (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    round_count INTEGER NOT NULL,
    status VARCHAR(255),
    has_password BOOLEAN NOT NULL,
    join_code VARCHAR(255) NOT NULL UNIQUE,
    host_user_id UUID NOT NULL,
    created_date TIMESTAMP NOT NULL,
    modified_date TIMESTAMP
);

-- room_user 테이블
CREATE TABLE room_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    room_id UUID REFERENCES room(id),
    user_id UUID NOT NULL,
    score INTEGER NOT NULL,
    created_date TIMESTAMP NOT NULL,
    modified_date TIMESTAMP
);

-- game 테이블
CREATE TABLE game (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    room_id UUID NOT NULL,
    round_count INTEGER NOT NULL,
    created_date TIMESTAMP NOT NULL,
    modified_date TIMESTAMP
);

-- game_user_score 테이블
CREATE TABLE game_user_score (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    score INTEGER NOT NULL,
    game_id UUID REFERENCES game(id)
);

-- quiz 테이블
CREATE TABLE quiz (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    game_id UUID REFERENCES game(id),
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(255) NOT NULL,
    creator_user_id UUID NOT NULL,
    round_number INTEGER NOT NULL,
    created_date TIMESTAMP NOT NULL,
    modified_date TIMESTAMP
);

-- chat_message 테이블
CREATE TABLE chat_message (
    id BIGSERIAL PRIMARY KEY,
    room_id VARCHAR(255) NOT NULL,
    sender VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    modified_date TIMESTAMP
); 