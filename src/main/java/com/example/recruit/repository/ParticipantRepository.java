package com.example.recruit.repository;

import com.example.recruit.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * File: ParticipantRepository.java
 * Description: participant 테이블 데이터베이스 접근 인터페이스
 */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    // 모집글 현재 참여 인원 수 조회
    int countByRecruitId(Long recruitId);

    // 참여 취소 시 해당 참여자 삭제
    Participant deleteByRecruitIdAndUserId(Long recruitId, String userId);

    // 모집글 ID로 참여자 목록 조회
    List<Participant> findByRecruitId(Long recruitId);

    // 모집글 삭제 시 연관 참여자 일괄 삭제
    void deleteByRecruitId(Long recruitId);
}
