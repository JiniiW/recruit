package com.example.recruit.repository;

import com.example.recruit.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * File: CommentRepository.java
 * Description: comment 테이블 데이터베이스 접근 인터페이스
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 상세 화면에서 해당 모집글의 댓글 목록을 보여줘야 함
    ArrayList<Comment> findByRecruitId(Long recruitId);

    // 모집글 삭제 시 연관 댓글 일괄 삭제
    void deleteByRecruitId(Long recruitId);
}

