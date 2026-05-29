package com.example.recruit.dto;

import lombok.*;

/**
 * File: CommentForm.java
 * Description: 댓글 작성 시 클라이언트로부터 전달받는 요청 데이터 객체
 */

// 댓글 작성 요청에서 들어오는 JSON 데이터 받음
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentForm {
    private String content;
}
