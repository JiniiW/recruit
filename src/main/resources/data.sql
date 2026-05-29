-- recruit 데이터
-- OPEN: 아직 자리 있음
INSERT INTO recruit (id, title, content, author_id, max_count, status, end_at, district)
VALUES (1, '백엔드 스터디 모집', '백엔드 개발에 관심 있는 분들 모집합니다.', 'user0', 5, 'OPEN', '2026-06-01 14:00:00', '서울');

INSERT INTO recruit (id, title, content, author_id, max_count, status, end_at, district)
VALUES (2, '프론트엔드 프로젝트 팀원 모집', '리액트 기반 프로젝트 함께할 팀원을 찾습니다.', 'user0', 3, 'OPEN', '2026-06-05 18:00:00', '경기');

-- FULL: 꽉 찬 상태
INSERT INTO recruit (id, title, content, author_id, max_count, status, end_at, district)
VALUES (3, '알고리즘 스터디 모집', '매주 알고리즘 문제를 같이 풀어요.', 'user0', 2, 'FULL', '2026-05-30 10:00:00', '서울');

-- CLOSED: 마감
INSERT INTO recruit (id, title, content, author_id, max_count, status, end_at, district)
VALUES (4, '디자인 프로젝트 모집', 'UI/UX 디자인 프로젝트 모집 마감되었습니다.', 'user0', 4, 'CLOSED', '2026-05-28 16:00:00', '부산');

INSERT INTO recruit (id, title, content, author_id, max_count, status, end_at, district)
VALUES (5, '마감 테스트 모집글', '마감일이 지난 테스트용 게시글입니다.', 'user0', 3, 'OPEN', '2026-05-25 00:00:00', '서울');

ALTER SEQUENCE recruit_seq RESTART WITH 100;


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