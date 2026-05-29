package com.example.recruit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.Id;

/**
 * File: Comment.java
 * Description: comment 테이블과 매핑되는 댓글 엔티티
 * JPA 앤티티 작성방식, 댓글 DB 테이블로 저장 위한 구조
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 고유 번호
    private Long recruitId; // 소속 모집글 ID
    private String userId; // 댓글 작성자
    private String content; // 댓글 내용
}