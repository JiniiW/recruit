package com.example.recruit.service;

import com.example.recruit.dto.ParticipantResponseForm;
import com.example.recruit.dto.RecruitForm;
import com.example.recruit.entity.Participant;
import com.example.recruit.entity.Recruit;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * File: RecruitService.java
 * Description: 모집글 CRUD 및 참여/취소, 상태 변경 비즈니스 로직 처리
 */

public interface RecruitService {

    // 모든 모집글 조회
    List<Recruit> index();

    // 모집글 확인
    Recruit show(Long id);

    // 모집글 생성
    Recruit create(RecruitForm dto);

    // 모집글 수정
    Recruit update(Long id, RecruitForm dto);

    // 모집글 삭제
    Recruit delete(Long id);

    // insert participant, update recruit
    ParticipantResponseForm join(Long recruitId, String userId);

    // delete participant, update recruit
    ParticipantResponseForm leave(Long recruitId, String userId);
}
