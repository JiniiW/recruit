package com.example.recruit.service;

import com.example.recruit.dto.ParticipantResponseForm;
import com.example.recruit.entity.Participant;
import com.example.recruit.entity.Recruit;
import org.springframework.stereotype.Service;

/**
 * File: RecruitService.java
 * Description: 모집글 CRUD 및 참여/취소, 상태 변경 비즈니스 로직 처리
 */

public interface RecruitService {
    Recruit show(Long id);

    // insert participant, update recruit
    ParticipantResponseForm join(Long recruitId, String userId);

    // delete participant, update recruit
    ParticipantResponseForm leave(Long recruitId, String userId);
}
