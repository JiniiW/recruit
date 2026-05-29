package com.example.recruit.service;

import com.example.recruit.dto.CommentForm;
import com.example.recruit.entity.Comment;
import com.example.recruit.entity.Recruit;
import com.example.recruit.repository.CommentRepository;
import com.example.recruit.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{

    // 댓글과 모집글 참조
    private final CommentRepository commentRepository;
    private final RecruitRepository recruitRepository;

    // 상세 화면에서 댓글 목록 가져오기
    @Override
    public List<Comment> index(Long recruitId) {
        return commentRepository.findByRecruitId(recruitId);
    }

    // 댓글을 달 모집글 존재 확인 + 댓글 내용이 빈 내용인지 + Comment 객체 생성 후 Repository로 저장
    @Override
    public Comment create(Long recruitId, String userId, CommentForm commentForm) {
        Recruit recruit = recruitRepository.findById(recruitId).orElse(null);

        if (recruit == null) {
            log.info("댓글 작성 실패: 모집글이 없습니다. recruitId=" + recruitId);
            return null;
        }

        if (commentForm.getContent() == null || commentForm.getContent().trim().isEmpty()) {
            log.info("댓글 작성 실패: 내용이 비어 있습니다.");
            return null;
        }

        Comment comment = new Comment(null, recruitId, userId, commentForm.getContent());

        return commentRepository.save(comment);
    }

    // 댓글 존재 확인 + 해당 모집글 댓글인지 확인 + 본인의 댓글인지 확인 후 삭제
    @Override
    public Comment delete(Long recruitId, Long commentId, String userId) {
        Comment target = commentRepository.findById(commentId).orElse(null);

        if (target == null) {
            log.info("댓글 삭제 실패: 댓글이 없습니다. commentId=" + commentId);
            return null;
        }

        if (!target.getRecruitId().equals(recruitId)) {
            log.info("댓글 삭제 실패: 모집글 번호가 일치하지 않습니다. recruitId=" + recruitId);
            return null;
        }
        // 본인이 쓴 댓글만 삭제 가능하게 만드는 핵심
        if (!target.getUserId().equals(userId)) {
            log.info("댓글 삭제 실패: 작성자가 아닙니다. userId=" + userId);
            return null;
        }

        commentRepository.delete(target);

        return target;
    }
}
