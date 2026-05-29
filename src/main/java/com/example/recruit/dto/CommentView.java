package com.example.recruit.dto;

import com.example.recruit.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

// 댓글 삭제가 본인 외 다은 유저도 보이는 현상 발생 ( 삭제는 댓글 작성자 api test 상 본인만 가능하긴 함 )
// 기존 Comment 엔티티를 그대로 화면에 넘김 -> mustache는 id와 content만 알고 있음 (비교를 못함)
// userId가 commentOwner와 같은지 확인 -> mustache 에서 {{#commentOwner}} ... {{/commentOwner}} 로 감쌈
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