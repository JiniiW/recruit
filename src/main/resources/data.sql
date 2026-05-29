-- ========================
-- RECRUIT 데이터
-- ========================
INSERT INTO recruit (title, content, author_id, max_count, status, end_at, district)
VALUES ('백엔드 스터디 모집', '백엔드 개발에 관심 있는 분들 모집합니다.', 'user1', 5, 'OPEN', '2026-06-01 14:00:00', '서울');

INSERT INTO recruit (title, content, author_id, max_count, status, end_at, district)
VALUES ('프론트엔드 프로젝트 팀원 모집', '리액트 기반 프로젝트 함께할 팀원을 찾습니다.', 'user2', 3, 'OPEN', '2026-06-05 18:00:00', '경기');

INSERT INTO recruit (title, content, author_id, max_count, status, end_at, district)
VALUES ('알고리즘 스터디 모집', '매주 알고리즘 문제를 같이 풀어요.', 'user1', 2, 'FULL', '2026-05-30 10:00:00', '서울');

INSERT INTO recruit (title, content, author_id, max_count, status, end_at, district)
VALUES ('디자인 프로젝트 모집', 'UI/UX 디자인 프로젝트 모집 마감되었습니다.', 'user3', 4, 'CLOSED', '2026-05-28 16:00:00', '부산');

INSERT INTO recruit (title, content, author_id, max_count, status, end_at, district)
VALUES ('마감 테스트 모집글', '마감일이 지난 테스트용 게시글입니다.', 'user2', 3, 'OPEN', '2026-05-25 00:00:00', '서울');

-- ========================
-- PARTICIPANT 데이터
-- ========================
INSERT INTO participant (recruit_id, user_id) VALUES (1, 'user2');
INSERT INTO participant (recruit_id, user_id) VALUES (1, 'user3');

INSERT INTO participant (recruit_id, user_id) VALUES (2, 'user1');
INSERT INTO participant (recruit_id, user_id) VALUES (2, 'user4');

INSERT INTO participant (recruit_id, user_id) VALUES (3, 'user2');
INSERT INTO participant (recruit_id, user_id) VALUES (3, 'user3');

-- ========================
-- COMMENT 데이터
-- ========================
INSERT INTO comment (recruit_id, user_id, content) VALUES (1, 'user2', '저도 백엔드 공부 중인데 참여하고 싶어요!');
INSERT INTO comment (recruit_id, user_id, content) VALUES (1, 'user3', '스터디 주기가 어떻게 되나요?');
INSERT INTO comment (recruit_id, user_id, content) VALUES (1, 'user1', '매주 토요일 오전에 진행할 예정입니다!');

INSERT INTO comment (recruit_id, user_id, content) VALUES (2, 'user1', '리액트 초보도 괜찮을까요?');
INSERT INTO comment (recruit_id, user_id, content) VALUES (2, 'user2', '네, 같이 배워가는 분위기입니다!');

INSERT INTO comment (recruit_id, user_id, content) VALUES (3, 'user2', '알고리즘 어느 수준부터 시작하나요?');
