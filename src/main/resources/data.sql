-- ========================
-- RECRUIT 데이터
-- ========================
INSERT INTO recruit (title, content, author_id, max_count, status, end_at, district)
VALUES ('치킨 공동배달 구해요', '교촌 치킨 반반 시킬 사람 구합니다. 배달비 나눠요!', 'kong', 4, 'OPEN', '2026-06-10 20:00:00', '역삼동');

INSERT INTO recruit (title, content, author_id, max_count, status, end_at, district)
VALUES ('알고리즘 스터디 모집', '백준 골드 목표! 주 2회 온라인으로 진행합니다.', 'luna', 4, 'OPEN', '2026-06-15 18:00:00', '서초구');

INSERT INTO recruit (title, content, author_id, max_count, status, end_at, district)
VALUES ('피자 같이 시킬 사람', '도미노 피자 라지 하나 나눠 먹을 공범 구합니다.', 'momo', 3, 'FULL', '2026-06-08 19:00:00', '강남구');

INSERT INTO recruit (title, content, author_id, max_count, status, end_at, district)
VALUES ('스프링 부트 스터디', 'JPA, Security 같이 공부해요. 주 1회 오프라인.', 'ryu', 4, 'CLOSED', '2026-05-20 10:00:00', '마포구');

INSERT INTO recruit (title, content, author_id, max_count, status, end_at, district)
VALUES ('마라탕 공동배달', '마라탕 혼자 시키기 아까운 분! 같이 시켜요.', 'nova', 3, 'OPEN', '2026-05-25 18:00:00', '송파구');

-- ========================
-- PARTICIPANT 데이터
-- ========================
-- recruit 1번 (max 4, author: kong) → kong 포함 2명
INSERT INTO participant (recruit_id, user_id) VALUES (1, 'kong');
INSERT INTO participant (recruit_id, user_id) VALUES (1, 'luna');

-- recruit 2번 (max 4, author: luna) → luna 포함 2명
INSERT INTO participant (recruit_id, user_id) VALUES (2, 'luna');
INSERT INTO participant (recruit_id, user_id) VALUES (2, 'momo');

-- recruit 3번 (max 3, author: momo) → FULL 상태, 3명
INSERT INTO participant (recruit_id, user_id) VALUES (3, 'momo');
INSERT INTO participant (recruit_id, user_id) VALUES (3, 'kong');
INSERT INTO participant (recruit_id, user_id) VALUES (3, 'ryu');

-- recruit 4번 (max 4, author: ryu) → CLOSED
INSERT INTO participant (recruit_id, user_id) VALUES (4, 'ryu');
INSERT INTO participant (recruit_id, user_id) VALUES (4, 'nova');

-- recruit 5번 (max 3, author: nova) → 마감일 지난 OPEN (자동 CLOSED 테스트용)
INSERT INTO participant (recruit_id, user_id) VALUES (5, 'nova');

-- ========================
-- COMMENT 데이터
-- ========================
INSERT INTO comment (recruit_id, user_id, content) VALUES (1, 'luna', '저 교촌 오리지널 먹고 싶은데 반반 가능할까요?');
INSERT INTO comment (recruit_id, user_id, content) VALUES (1, 'kong', '네 상관없어요! 같이 시켜요~');
INSERT INTO comment (recruit_id, user_id, content) VALUES (1, 'momo', '저도 끼워주세요!');

INSERT INTO comment (recruit_id, user_id, content) VALUES (2, 'momo', '골드 하위권인데 참여 가능한가요?');
INSERT INTO comment (recruit_id, user_id, content) VALUES (2, 'luna', '물론이죠! 같이 올라가요 ㅎㅎ');

INSERT INTO comment (recruit_id, user_id, content) VALUES (3, 'kong', '피자 사이즈가 어떻게 되나요?');
INSERT INTO comment (recruit_id, user_id, content) VALUES (3, 'momo', '라지 사이즈입니다!');