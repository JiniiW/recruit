package com.example.recruit.controller;

import com.example.recruit.dto.ParticipantResponseForm;
import com.example.recruit.entity.Participant;
import com.example.recruit.service.RecruitService;
import jakarta.servlet.http.Part;
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

    // 참여신청
    @PostMapping("/{id}/join")
    public ResponseEntity<ParticipantResponseForm> join(@PathVariable Long id, @RequestParam String userId, RedirectAttributes rttr){
        ParticipantResponseForm join = recruitService.join(id, userId);
//        if(join != null){
//            rttr.addFlashAttribute("msg", "참가.");
//        }
        return (join != null) ? ResponseEntity.ok(join) : ResponseEntity.notFound().build();
    }

    // 참여취소
    @PostMapping("/{id}/leave")
    public ResponseEntity<ParticipantResponseForm> leave(@PathVariable Long id, @RequestParam String userId, RedirectAttributes rttr){
        ParticipantResponseForm leave = recruitService.leave(id, userId);
//        if(leave != null){
//            rttr.addFlashAttribute("msg", "참가 취소.");
//        }
        return (leave != null) ? ResponseEntity.ok(leave) : ResponseEntity.badRequest().build();
    }
}
