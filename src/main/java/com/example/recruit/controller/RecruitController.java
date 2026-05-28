package com.example.recruit.controller;

import com.example.recruit.service.RecruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/list")
    public String list(@RequestParam String userId, Model model){
        model.addAttribute("userId", userId);
        return "recruits/list";
    }

    @GetMapping("/list/{id}")
    public String detail(@PathVariable Long id, @RequestParam String userId, Model model){
        model.addAttribute("id", id);
        model.addAttribute("userId", userId);
        recruitService.show(id);
        return "recruits/detail";
    }
}

