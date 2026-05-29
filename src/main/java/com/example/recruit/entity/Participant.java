package com.example.recruit.entity;

import com.example.recruit.repository.ParticipantRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * File: Participant.java
 * Description: participant 테이블과 매핑되는 참여자 엔티티
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
@Getter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long recruitId;
    private String userId;

    public Participant(Long recruitId, String userId) {
        this.recruitId = recruitId;
        this.userId = userId;
    }
}
