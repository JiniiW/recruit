package com.example.recruit.service;

import com.example.recruit.dto.ParticipantResponseForm;
import com.example.recruit.dto.RecruitForm;
import com.example.recruit.entity.Participant;
import com.example.recruit.entity.Recruit;

import java.util.List;

/**
 * File: RecruitService.java
 * Description: 모집글 CRUD 및 참여/취소, 상태 변경 비즈니스 로직 인터페이스
 */
public interface RecruitService {

    // 모든 모집글 조회
    List<Recruit> index();

    // 단건 모집글 조회
    Recruit show(Long id);

    // 모집글 생성
    Recruit create(RecruitForm dto);

    // 모집글 수정
    Recruit update(Long id, RecruitForm dto);

    // 모집글 삭제
    Recruit delete(Long id);

    // 모집글 참여자 목록 조회
    List<Participant> getParticipants(Long recruitId);

    // 참가 신청
    ParticipantResponseForm join(Long recruitId, String userId);

    // 참가 취소
    ParticipantResponseForm leave(Long recruitId, String userId);
}
