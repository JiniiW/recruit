package com.example.recruit.controller;

import com.example.recruit.entity.Recruit;
import com.example.recruit.repository.RecruitRepository;
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

import java.util.List;

/**
 * File: RecruitController.java
 * Description: GET 요청을 처리하여 머스태치 화면을 반환하는 뷰 컨트롤러
 */
@Controller
@RequestMapping("/recruits")
@RequiredArgsConstructor
@Slf4j
public class RecruitController {

    private final RecruitService recruitService;

    private final RecruitRepository recruitRepository; // 변경

    // 1. 대표 화면 (모든 모집글)
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
        model.addAttribute("recruit", recruit);
        model.addAttribute("userId", userId);
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
        Recruit recruit = recruitRepository.findById(id).orElse(null);

        model.addAttribute("recruit", recruit);
        return "recruits/form";
    }
}

