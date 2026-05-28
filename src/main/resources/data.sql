/*
-- recruit 테이블 (4개: OPEN 2, FULL 1, CLOSED 1)
INSERT INTO recruit (id, title, content, author_id, password, max_count, end_at, status)
VALUES
    (1, '[지역]편의점 1+1 같이 먹을 사람', 'GS25에서 과자 1+1 행사 중인데 혼자 먹기 많아요. 반 나눠요!', 'Auser', 1234, 3, '2026-06-01 18:00:00', 'OPEN'),
    (2, '[지역]배달 최소금액 채우실 분', '치킨 시키려는데 2만원 안 나와요. 같이 시켜요!', 'Buser', 5678, 2, '2026-05-30 12:00:00', 'FULL'),
    (3, '[지역]오락실 2인 게임 같이 하실 분', '철권 같이 할 사람 구해요. 실력 무관!', 'Cuser', 1111, 2, '2026-05-01 20:00:00', 'CLOSED'),
    (4, '[지역]야식 같이 시킬 사람 구해요', '떡볶이 시키려는데 혼자 먹기 너무 많아요!', 'Duser', 2222, 4, '2026-06-05 22:00:00', 'OPEN');

-- participant 테이블
-- FULL(2번) 글: 2명 참여 (max_count 채움)
-- OPEN(1번) 글: 1명 참여
-- CLOSED(3번) 글: 1명 참여
-- OPEN(4번) 글: 참여자 없음

INSERT INTO participant (id, recruit_id, user_id)
VALUES
    (1, 2, 'Cuser'),
    (2, 2, 'Duser'),
    (3, 1, 'Buser'),
    (4, 3, 'Auser');

-- comment 테이블
-- 1번 글: 댓글 2개
-- 2번 글: 댓글 2개
-- 3번 글: 댓글 1개
-- 4번 글: 댓글 없음
INSERT INTO comment (id, recruit_id, user_id, content)
VALUES
    (1, 1, 'Buser', '몇 시에 만나요?'),
    (2, 1, 'Auser', '오후 6시 어때요?'),
    (3, 2, 'Cuser', '어디서 만날까요?'),
    (4, 2, 'Duser', '편의점 앞에서 만나요!'),
    (5, 3, 'Buser', '결국 못 갔네요 ㅠㅠ');
 */

-- recruit 데이터
-- OPEN: 아직 자리 있음
INSERT INTO recruit (id, title, max_count, status, end_date) VALUES (1, '백엔드 스터디 모집', 5, 'OPEN', '2026-06-01 14:00:00');
INSERT INTO recruit (id, title, max_count, status, end_date) VALUES (2, '프론트엔드 프로젝트 팀원 모집', 3, 'OPEN', '2026-06-05 18:00:00');
-- FULL: 꽉 찬 상태
INSERT INTO recruit (id, title, max_count, status, end_date) VALUES (3, '알고리즘 스터디 모집', 2, 'FULL', '2026-05-30 10:00:00');
-- CLOSED: 마감
INSERT INTO recruit (id, title, max_count, status, end_date) VALUES (4, '디자인 프로젝트 모집', 4, 'CLOSED', '2026-05-28 16:00:00');
INSERT INTO recruit (id, title, max_count, status, end_date) VALUES (5, '마감 테스트 모집글', 3, 'OPEN', '2026-05-25 00:00:00');

-- participant 데이터
-- recruit 1번 (max 5) → 3명 참여 중 (OPEN 유지)
INSERT INTO participant (recruit_id, user_id) VALUES (1, 'user1');
INSERT INTO participant (recruit_id, user_id) VALUES (1, 'user2');
INSERT INTO participant (recruit_id, user_id) VALUES (1, 'user3');

-- recruit 2번 (max 3) → 2명 참여 중 (OPEN 유지)
INSERT INTO participant (recruit_id, user_id) VALUES (2, 'user1');
INSERT INTO participant (recruit_id, user_id) VALUES (2, 'user4');

-- recruit 3번 (max 2) → 2명 참여 중 (FULL 상태)
INSERT INTO participant (recruit_id, user_id) VALUES (3, 'user2');
INSERT INTO participant (recruit_id, user_id) VALUES (3, 'user3');

-- recruit 4번 (CLOSED) → 참여자 없음