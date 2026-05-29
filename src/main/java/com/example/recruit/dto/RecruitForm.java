package com.example.recruit.dto;

import com.example.recruit.entity.Recruit;
import com.example.recruit.entity.RecruitStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * File: RecruitForm.java
 * Description: 댓글 작성 시 클라이언트로부터 전달받는 요청 데이터 객체
 */

@AllArgsConstructor
@ToString
@Getter
public class RecruitForm {

    private Long id;
    private String title;
    private String content;
    private String authorId;
    private int maxCount;
    private RecruitStatus status;
    private LocalDateTime endAt;
    private String district;

    public Recruit toEntity() {
        return new Recruit(id, title, content, this.authorId, maxCount, RecruitStatus.OPEN, endAt, district);
    }
}
