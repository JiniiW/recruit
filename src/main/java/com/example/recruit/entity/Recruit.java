package com.example.recruit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * File: Recruit.java
 * Description: recruit 테이블과 매핑되는 모집글 엔티티
 */
@Entity
@NoArgsConstructor
@ToString
@Slf4j
@Getter
@Setter
public class Recruit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String title;

    private int maxCount;

    @Enumerated(EnumType.STRING)
    private RecruitStatus status = RecruitStatus.OPEN;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

}
