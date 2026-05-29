package com.example.recruit.entity;

import com.example.recruit.dto.RecruitForm;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * File: Recruit.java
 * Description: recruit 테이블과 매핑되는 모집글 엔티티
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Recruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String authorId;
    private int maxCount;

    @Enumerated(EnumType.STRING)
    private RecruitStatus status = RecruitStatus.OPEN;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endAt;
    private String district;

    // 수정 시 null이 아닌 필드만 반영
    public void patch(RecruitForm dto) {
        if (dto.getTitle() != null)
            this.title = dto.getTitle();
        if (dto.getContent() != null)
            this.content = dto.getContent();
        if (dto.getDistrict() != null)
            this.district = dto.getDistrict();
        if (dto.getMaxCount() > 0)
            this.maxCount = dto.getMaxCount();
        if (dto.getEndAt() != null)
            this.endAt = dto.getEndAt();
    }

    // Mustache 화면 출력용 마감시간 포맷 변환
    public String getEndAtFormatted(){
        if (endAt == null) return "";
        return endAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // Mustache 상태 분기용 메서드
    public boolean isOpen() { return this.status == RecruitStatus.OPEN; }
    public boolean isFull() { return this.status == RecruitStatus.FULL; }
    public boolean isClosed() { return this.status == RecruitStatus.CLOSED; }

}
