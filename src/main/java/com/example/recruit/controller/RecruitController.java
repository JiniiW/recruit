package com.example.recruit.controller;

import com.example.recruit.dto.CommentView;
import com.example.recruit.entity.Comment;
import com.example.recruit.entity.Participant;
import com.example.recruit.entity.Recruit;
import com.example.recruit.entity.RecruitStatus;
import com.example.recruit.repository.ParticipantRepository;
import com.example.recruit.repository.RecruitRepository;
import com.example.recruit.service.CommentService;
import com.example.recruit.service.RecruitService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * File: RecruitController.java
 * Description: GET 요청을 처리하여 머스태치 화면을 반환하는 뷰 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/recruits")
public class RecruitController {

    private final RecruitService recruitService;
    private final CommentService commentService;

    private final RecruitRepository recruitRepository; // 변경필요

    // 1. 모집글 전체 목록 화면
    @GetMapping
    public String list(@RequestParam String userId, Model model){
        log.info("모집글 전체 목록 화면 요청");

        List<Recruit> recruits = recruitService.index();
        model.addAttribute("recruits", recruits);
        model.addAttribute("userId", userId);

        return "recruits/list";
    }

    // 2. 모집글 상세 화면
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, @RequestParam String userId, Model model) {
        log.info("모집글 상세 화면 요청 - ID: {}", id);

        Recruit recruit = recruitService.show(id);
        if (recruit == null) {
            return "redirect:/recruits?userId=" + userId;
        }

        model.addAttribute("recruit", recruit);
        model.addAttribute("userId", userId);


        // 댓글 엔티티 목록 가져오고 (댓글 번호에 따라)
        List<Comment> comments = commentService.index(id);
        // CommentView DTO로 바꿔서 댓글을 넘김
        List<CommentView> commentViews = new ArrayList<>();
        // 댓글 하나씩 검사, 현재 접속자가 댓글 작성자인지
        for (Comment comment : comments) {
            commentViews.add(CommentView.from(comment, userId));
        }

        List<Participant> participants = recruitService.getParticipants(id);

        boolean isAuthor = false;
        if (recruit.getAuthorId().equals(userId)) {
            isAuthor = true;
        }

        // 작성자 및 참여자 여부 판별 - 버튼 분기에 사용
        boolean isParticipant = false;
        for (Participant participant : participants) {
            if (participant.getUserId().equals(userId)) {
                isParticipant = true;
                break;
            }
        }

        model.addAttribute("comments", commentViews);
        model.addAttribute("participants", participants);
        model.addAttribute("isAuthor", isAuthor);
        model.addAttribute("isParticipant", isParticipant);

        model.addAttribute("currentCount", participants.size());
        model.addAttribute("recruitId", id);

        return "recruits/detail";
    }

    // 3. 모집글 등록 폼
    @GetMapping("/new")
    public String newRecruitForm(@RequestParam String userId, Model model) {
        model.addAttribute("userId", userId);
        return "recruits/form";
    }

    // 4. 모집글 수정 폼
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam String userId, Model model) {
        model.addAttribute("userId", userId);

        // 기존 작성 글
        Recruit recruit = recruitService.show(id);

        // 현재 참여 인원
        List<Participant> participants = recruitService.getParticipants(id);
        model.addAttribute("currentCount", participants.size());

        model.addAttribute("recruit", recruit);
        return "recruits/form";
    }
}

