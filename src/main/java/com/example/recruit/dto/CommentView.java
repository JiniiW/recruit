package com.example.recruit.dto;

import com.example.recruit.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
/**
 * File: CommentView.java
 * Description: 댓글 목록 화면 렌더링용 DTO
 *              댓글 작성자 여부(commentOwner)를 포함하여 Mustache 버튼 분기에 사용
 */
@AllArgsConstructor
@Getter
@ToString
public class CommentView {
    private Long id;
    private Long recruitId;
    private String userId;
    private String content;
    private boolean commentOwner;

    public static CommentView from(Comment comment, String loginUserId) {
        boolean commentOwner = comment.getUserId().equals(loginUserId);

        return new CommentView(
                comment.getId(),
                comment.getRecruitId(),
                comment.getUserId(),
                comment.getContent(),
                commentOwner
        );
    }
}