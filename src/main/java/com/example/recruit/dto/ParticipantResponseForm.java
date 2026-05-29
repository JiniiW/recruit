package com.example.recruit.dto;

import com.example.recruit.entity.RecruitStatus;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
/**
 * File: ParticipantResponseForm.java
 * Description: 참여 신청/취소 처리 결과를 반환하는 응답 DTO
 *              참여 상태, 모집 상태, 현재 인원 등을 포함
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class ParticipantResponseForm {
    private Long recruitId;
    private String userId;
    private String participantStatus;
    private RecruitStatus recruitStatus;
    private int currentCount;
    private int maxCount;

    // 참여 처리 결과 로그 출력
    public void logInfo(){
        log.info("recruitId : {}, userId : {}, participantStatus : {}, recruitStatus : {}, currentCount : {}, maxCount : {}",recruitId, userId, participantStatus, recruitStatus, currentCount, maxCount);
    }
}
