package com.example.recruit.repository;

import com.example.recruit.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * File: CommentRepository.java
 * Description: comment 데이터베이스 접근 인터페이스
 */

// JPA Repository 구조, 크루드 기본 DB가능 사용
public interface CommentRepository extends CrudRepository<Comment, Long> {

    // 상세 화면에서 해당 모집글의 댓글 목록을 보여줘야 함
    ArrayList<Comment> findByRecruitId(Long recruitId);

    // 모집글 삭제시 댓글도 삭제
    ArrayList<Comment> deleteByRecruitId(Long recruitId);
}

