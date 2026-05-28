package com.example.recruit.dto;

import com.example.recruit.entity.RecruitStatus;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

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

    public void logInfo(){
        log.info("recruitId : {}, userId : {}, participantStatus : {}, recruitStatus : {}, currentCount : {}, maxCount : {}",recruitId, userId, participantStatus, recruitStatus, currentCount, maxCount);
    }
}
