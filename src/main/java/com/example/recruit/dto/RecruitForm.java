package com.example.recruit.dto;

import com.example.recruit.entity.Recruit;
import com.example.recruit.entity.RecruitStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

/**
 * File: RecruitForm.java
 * Description: 모집글 생성/수정 시 클라이언트로부터 전달받는 요청 데이터 객체
 */

@AllArgsConstructor
@ToString
@Getter
@Setter
public class RecruitForm {

    private Long id;
    private String title;
    private String content;
    private String authorId;
    private int maxCount;
    private RecruitStatus status;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endAt;

    private String district;

    public Recruit toEntity() {
        return new Recruit(id, title, content, this.authorId, maxCount, RecruitStatus.OPEN, endAt, district);
    }
}
