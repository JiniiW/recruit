package com.example.recruit.service;

import com.example.recruit.dto.ParticipantResponseForm;
import com.example.recruit.entity.Participant;
import com.example.recruit.entity.Recruit;
import com.example.recruit.entity.RecruitStatus;
import com.example.recruit.repository.ParticipantRepository;
import com.example.recruit.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitServiceImpl implements RecruitService{

    private final ParticipantRepository participantRepository;
    private final RecruitRepository recruitRepository;

    @Override
    public ParticipantResponseForm join(Long recruitId, String userId) {

        // insert participant
        Participant participant = new Participant(recruitId, userId);
        participantRepository.save(participant);

        //
        ParticipantResponseForm response = setParticipantResponse(recruitId, userId, "join");

        if (response == null) return null;

        if (response.getCurrentCount() >= response.getMaxCount()) {
            Recruit recruit = recruitRepository.findById(recruitId).orElseThrow(() -> new RuntimeException("해당하는 모집글이 없습니다. ID=" + recruitId));
            recruit.setStatus(RecruitStatus.FULL);
            recruitRepository.save(recruit); //저장
            response.setRecruitStatus(RecruitStatus.FULL);
        }

        response.logInfo();

        return response;
    }

    @Override
    public ParticipantResponseForm leave(Long recruitId, String userId) {

        // delete participant
        participantRepository.deleteByRecruitIdAndUserId(recruitId, userId);

        ParticipantResponseForm response = setParticipantResponse(recruitId, userId, "leave");

        if (response == null) return null;

        if (response.getCurrentCount() < response.getMaxCount()) {
            Recruit recruit = recruitRepository.findById(recruitId).orElseThrow(() -> new RuntimeException("해당하는 모집글이 없습니다. ID=" + recruitId));
            recruit.setStatus(RecruitStatus.OPEN);
            recruitRepository.save(recruit);
            response.setRecruitStatus(RecruitStatus.OPEN);
        }

        response.logInfo();
        return response;

    }

    @Override
    public Recruit show(Long id){
        Recruit recruit = recruitRepository.findById(id).orElse(null);
        if (recruit == null) return null;

        updateRecruitStatus(recruit);

        return recruit;
    }

    // ParticipantResponseForm 세팅
    private ParticipantResponseForm setParticipantResponse(Long recruitId, String userId, String participantStatus) {
        Recruit recruit = recruitRepository.findById(recruitId).orElse(null);

        if (recruit == null) {
            log.info("해당하는 모집글이 없습니다. ID=" + recruitId);
            return null;
        }

        int currentCount = participantRepository.countByRecruitId(recruitId);

        ParticipantResponseForm form = new ParticipantResponseForm();
        form.setRecruitId(recruitId);
        form.setUserId(userId);
        form.setParticipantStatus(participantStatus);
        form.setCurrentCount(currentCount);
        form.setMaxCount(recruit.getMaxCount());
        form.setRecruitStatus(recruit.getStatus());

        return form;
    }

    // 모집글 마감시간 CLOSED 처리 -> show()에서 테스트
    private void updateRecruitStatus(Recruit recruit){
        if (recruit.getEndDate() != null
                && LocalDateTime.now().isAfter(recruit.getEndDate())
                && recruit.getStatus() != RecruitStatus.CLOSED) {
            recruit.setStatus(RecruitStatus.CLOSED);
            recruitRepository.save(recruit);
            log.info("모집 마감 CLOSED. ID={}", recruit.getId());
        }
    }
}
