package com.example.recruit.service;

import com.example.recruit.dto.ParticipantResponseForm;
import com.example.recruit.dto.RecruitForm;
import com.example.recruit.entity.Participant;
import com.example.recruit.entity.Recruit;
import com.example.recruit.entity.RecruitStatus;
import com.example.recruit.repository.CommentRepository;
import com.example.recruit.repository.ParticipantRepository;
import com.example.recruit.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitServiceImpl implements RecruitService{

    private final RecruitRepository recruitRepository;
    private final ParticipantRepository participantRepository;
    private final CommentRepository commentRepository;

    // 1. 모든 모집글 조회
    @Override
    public List<Recruit> index() {
        updateRecruitStatus();
        return recruitRepository.findAll();
    }

     // 2. 단건 모집글 조회
     @Override
     public Recruit show(Long id){
         Recruit recruit = recruitRepository.findById(id).orElse(null);
         if (recruit == null) return null;

         updateRecruitStatus();

         return recruit;
     }

    // 3. 모집글 생성
    @Override
    public Recruit create(RecruitForm dto) {
        // 1. 글 생성
        Recruit recruit = recruitRepository.save(dto.toEntity());

        if (recruit == null) {
            log.info("참여자 없음");
        }

        // 2. 작성자를 첫 번째 참여자로
        Participant author = new Participant(recruit.getId(), dto.getAuthorId());
        participantRepository.save(author);

        return recruit;
    }

    // 4. 모집글 수정
    @Override
    public Recruit update(Long id, RecruitForm dto) {
        Recruit target = recruitRepository.findById(id).orElse(null);

        if (target == null) {
            log.info("수정 실패: 대상 엔티티가 없습니다. ID=" + id);
            return null;
        }

        target.patch(dto);
        Recruit updated = recruitRepository.save(target);
        log.info("수정 완료: " + target.toString());
        return updated;
    }

    // 5. 모집글 삭제
    @Override
    public Recruit delete(Long id) {
        Recruit target = recruitRepository.findById(id).orElse(null);

        if (target == null) {
            log.info("해당 Recruit이 없습니다. recruit, {}", target);
            return null;
        }

        recruitRepository.delete(target);
        participantRepository.deleteByRecruitId(id);
        commentRepository.deleteByRecruitId(id);

        return target;
    }

    // 6. 모집글의 참여자
    @Override
    public List<Participant> getParticipants(Long recruitId) {
        return participantRepository.findByRecruitId(recruitId);
    }

    // 7. 모집글 참여
    @Override
    public ParticipantResponseForm join(Long recruitId, String userId) {

        // insert participant
        Participant participant = new Participant(recruitId, userId);
        participantRepository.save(participant);

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

    // 모집글 참여 취소
    @Override
    @Transactional
    public ParticipantResponseForm leave(Long recruitId, String userId) {

        // delete participant
        Participant participant = participantRepository.deleteByRecruitIdAndUserId(recruitId, userId);

        if (participant == null) {
            log.warn("참여 취소 실패 - 참여 기록 없음: recruitId={}, userId={}", recruitId, userId);
            return null;
        }

        ParticipantResponseForm response = setParticipantResponse(recruitId, userId, "leave");

        if (response == null) {
            log.warn("참여 취소 실패 - 응답 생성 실패: recruitId={}, userId={}", recruitId, userId);
            return null;
        }

        if (response.getCurrentCount() < response.getMaxCount()) {
            Recruit recruit = recruitRepository.findById(recruitId).orElseThrow(() -> new RuntimeException("해당하는 모집글이 없습니다. ID=" + recruitId));
            recruit.setStatus(RecruitStatus.OPEN);
            recruitRepository.save(recruit);
            response.setRecruitStatus(RecruitStatus.OPEN);
        }

        response.logInfo();
        return response;

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

    // 모집글 마감시간 CLOSED 처리
    private void updateRecruitStatus() {
        List<Recruit> recruits = recruitRepository.findAll();
        for (Recruit recruit : recruits) {
            if (recruit.getEndAt() != null
                    && LocalDateTime.now().isAfter(recruit.getEndAt())
                    && recruit.getStatus() != RecruitStatus.CLOSED) {
                recruit.setStatus(RecruitStatus.CLOSED);
                recruitRepository.save(recruit);
                log.info("모집 마감 CLOSED. ID={}", recruit.getId());
            }
        }
    }
}
