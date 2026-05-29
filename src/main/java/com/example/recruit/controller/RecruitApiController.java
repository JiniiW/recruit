package com.example.recruit.controller;

import com.example.recruit.dto.CommentForm;
import com.example.recruit.dto.ParticipantResponseForm;
import com.example.recruit.dto.RecruitForm;
import com.example.recruit.entity.Comment;
import com.example.recruit.entity.Recruit;
import com.example.recruit.service.CommentService;
import com.example.recruit.service.RecruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * File: RecruitApiController.java
 * Description: 모집글/참여/댓글의 데이터 처리 요청을 받아 JSON을 반환하는 API 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruits")
public class RecruitApiController {

    private final RecruitService recruitService;
    private final CommentService commentService;

    // 1. 모집글 생성
    @PostMapping
    public ResponseEntity<Recruit> create(@RequestBody RecruitForm dto) {
        Recruit created = recruitService.create(dto);

        return (created != null) ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();


    }

    // 2. 모집글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Recruit> update(@PathVariable Long id, @RequestBody RecruitForm dto) {

        Recruit updated = recruitService.update(id, dto);

        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // 3. 모집글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Recruit> delete(@PathVariable Long id) {

        Recruit deleted = recruitService.delete(id);

        return (deleted != null)? ResponseEntity.ok(deleted) : ResponseEntity.notFound().build();
    }

    // 4. 댓글 작성
    @PostMapping("/{recruitId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable("recruitId") Long recruitId,
                                                 @RequestParam String userId,
                                                 @RequestBody CommentForm commentForm) {
        log.info("댓글 작성 API 요청 - recruitId: {}, userId: {}", recruitId, userId);

        Comment created = commentService.create(recruitId, userId, commentForm);

        if (created == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(created);
    }

    // 5. 댓글 삭제
    @DeleteMapping("/{recruitId}/comments/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("recruitId") Long recruitId,
                                                 @PathVariable("commentId") Long commentId,
                                                 @RequestParam String userId) {
        log.info("댓글 삭제 API 요청 - recruitId: {}, commentId: {}, userId: {}", recruitId, commentId, userId);

        Comment deleted = commentService.delete(recruitId, commentId, userId);

        if (deleted == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(deleted);
    }

    // 6. 참여 신청
    @PostMapping("/{recruitId}/join")
    public ResponseEntity<ParticipantResponseForm> join(@PathVariable Long recruitId,
                                                        @RequestParam String userId){
        log.info("참여 신청 요청: recruitId={}, userId={}", recruitId, userId);

        ParticipantResponseForm join = recruitService.join(recruitId, userId);

        return (join != null) ? ResponseEntity.ok(join) : ResponseEntity.notFound().build();
    }

    // 7. 참여 취소
    @DeleteMapping("/{recruitId}/leave")
    public ResponseEntity<ParticipantResponseForm> leave(@PathVariable Long recruitId,
                                                         @RequestParam String userId){
        log.info("참여 취소 요청: recruitId={}, userId={}", recruitId, userId);

        ParticipantResponseForm leave = recruitService.leave(recruitId, userId);

        return (leave != null) ? ResponseEntity.ok(leave) : ResponseEntity.notFound().build();
    }
}
