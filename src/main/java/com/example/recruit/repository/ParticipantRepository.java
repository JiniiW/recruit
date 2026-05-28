package com.example.recruit.repository;

import com.example.recruit.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * File: ParticipantRepository.java
 * Description: ParticipantRepository 데이터베이스 접근 인터페이스
 */

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    int countByRecruitId(Long recruitId);
    @Transactional
    void deleteByRecruitIdAndUserId(Long recruitId, String userId);
}
