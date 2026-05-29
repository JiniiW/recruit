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
/**
 * File: RecruitServiceImpl.java
 * Description: RecruitService 구현체 - 모집글 CRUD 및 참여/취소, 상태 변경 처리
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService{

    private final RecruitRepository recruitRepository;
    private final ParticipantRepository participantRepository;
    private final CommentRepository commentRepository;

    // 1. 모든 모집글 조회 (마감시간 체크 후 반환)
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
    @Transactional
    public Recruit create(RecruitForm dto) {
        Recruit recruit = recruitRepository.save(dto.toEntity());

        if (recruit == null) {
            log.info("참여자 없음");
        }

        // 작성자를 첫 번째 참여자로 등록
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

        // maxCount 변경 시 현재 인원과 비교해서 상태 재검증
        int currentCount = participantRepository.countByRecruitId(id);
        if (currentCount >= target.getMaxCount()) {
            target.setStatus(RecruitStatus.FULL);
        } else {
            target.setStatus(RecruitStatus.OPEN);
        }

        Recruit updated = recruitRepository.save(target);
        log.info("모집글 수정 완료: id={}", id);
        return updated;
    }

    // 5. 모집글 삭제 (연관 참여자, 댓글 일괄 삭제)
    @Override
    @Transactional
    public Recruit delete(Long id) {
        Recruit target = recruitRepository.findById(id).orElse(null);

        if (target == null) {
            log.info("해당 Recruit이 없습니다. recruit, {}", target);
            return null;
        }

        participantRepository.deleteByRecruitId(id);
        commentRepository.deleteByRecruitId(id);
        recruitRepository.delete(target);

        log.info("모집글 삭제 완료: id={}", id);
        return target;
    }

    // 6. 모집글 참여자 목록 조회
    @Override
    public List<Participant> getParticipants(Long recruitId) {
        return participantRepository.findByRecruitId(recruitId);
    }

    // 7. 참여 신청 (참여자 저장 후 인원 초과 시 FULL 처리)
    @Override
    @Transactional
    public ParticipantResponseForm join(Long recruitId, String userId) {
        Participant participant = new Participant(recruitId, userId);
        participantRepository.save(participant);

        ParticipantResponseForm response = setParticipantResponse(recruitId, userId, "join");
        if (response == null) {
            log.warn("참여 신청 실패 - 응답 생성 실패: recruitId={}, userId={}", recruitId, userId);
            return null;
        }

        // 현재 인원이 최대 인원 이상이면 FULL 처리
        if (response.getCurrentCount() >= response.getMaxCount()) {
            Recruit recruit = recruitRepository.findById(recruitId)
                    .orElseThrow(() -> new RuntimeException("해당하는 모집글이 없습니다. ID=" + recruitId));
            recruit.setStatus(RecruitStatus.FULL);          // DB 상태 변경
            recruitRepository.save(recruit);                // DB 저장
            response.setRecruitStatus(RecruitStatus.FULL);  // 응답 DTO 동기화
        }

        response.logInfo();
        return response;
    }

    // 8. 참여 취소 (참여자 삭제 후 인원 미달 시 OPEN 처리)
    @Override
    @Transactional
    public ParticipantResponseForm leave(Long recruitId, String userId) {
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

        // 현재 인원이 최대 인원 미만이면 OPEN 처리
        if (response.getCurrentCount() < response.getMaxCount()) {
            Recruit recruit = recruitRepository.findById(recruitId)
                    .orElseThrow(() -> new RuntimeException("해당하는 모집글이 없습니다. ID=" + recruitId));
            recruit.setStatus(RecruitStatus.OPEN);          // DB 상태 변경
            recruitRepository.save(recruit);                // DB 저장
            response.setRecruitStatus(RecruitStatus.OPEN);  // 응답 DTO 동기화
        }

        response.logInfo();
        return response;

    }

    // 참여 응답 DTO 생성 (현재 참여 인원, 모집 상태 포함)
    private ParticipantResponseForm setParticipantResponse(Long recruitId, String userId, String participantStatus) {
        Recruit recruit = recruitRepository.findById(recruitId).orElse(null);

        if (recruit == null) {
            log.warn("응답 생성 실패 - 모집글 없음: recruitId={}", recruitId);
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

    // 마감시간 지난 모집글 CLOSED 처리 (index/show 호출 시 실행)
    private void updateRecruitStatus() {
        List<Recruit> recruits = recruitRepository.findAll();
        for (Recruit recruit : recruits) {
            if (recruit.getEndAt() != null
                    && LocalDateTime.now().isAfter(recruit.getEndAt())
                    && recruit.getStatus() != RecruitStatus.CLOSED) {
                recruit.setStatus(RecruitStatus.CLOSED);
                recruitRepository.save(recruit);
                log.info("모집 마감 처리 완료: id={}", recruit.getId());
            }
        }
    }
}
