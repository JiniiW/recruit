package com.example.recruit.controller;

import com.example.recruit.dto.ParticipantResponseForm;
import com.example.recruit.dto.RecruitForm;
import com.example.recruit.entity.Recruit;
import com.example.recruit.service.RecruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // 1. 모집글 생성
    @PostMapping
    public Recruit create(@RequestBody RecruitForm dto) {
        log.info("모집글 생성 - 상세정보: {}", dto.toString());

        return recruitService.create(dto);

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

    // 참여신청
    @PostMapping("/{id}/join")
    public ResponseEntity<ParticipantResponseForm> join(@PathVariable Long id, @RequestParam String userId, RedirectAttributes rttr){
        ParticipantResponseForm join = recruitService.join(id, userId);
        return (join != null) ? ResponseEntity.ok(join) : ResponseEntity.notFound().build();
    }

    // 참여취소
    @PostMapping("/{id}/leave")
    public ResponseEntity<ParticipantResponseForm> leave(@PathVariable Long id, @RequestParam String userId, RedirectAttributes rttr){
        ParticipantResponseForm leave = recruitService.leave(id, userId);
        return (leave != null) ? ResponseEntity.ok(leave) : ResponseEntity.badRequest().build();
    }
}
