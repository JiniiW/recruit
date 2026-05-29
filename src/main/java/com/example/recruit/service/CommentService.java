package com.example.recruit.service;

import com.example.recruit.dto.CommentForm;
import com.example.recruit.entity.Comment;

import java.util.List;


/**
 * File: CommentService.java
 * Description: 작성/삭제 및 본인 확인 비즈니스 로직 처리
 */
public interface CommentService {
    // 특정 모집글 댓글 목록 조회
    List<Comment> index(Long recruitId);

    // 댓글 작성 API로 사용
    Comment create(Long recruitId, String userId, CommentForm commentForm);

    // 댓글 삭제 API로 사용 ( 남의 댓글 삭제 막을려고 recruitId 사용 )
    Comment delete(Long recruitId, Long commentId, String userId);
}
