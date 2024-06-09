package com.example.springsecurity.controller;

import com.example.springsecurity.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MainPageService mainPageService;

    @GetMapping("/")
    public String mainPage(Model model) {

        String id = mainPageService.getUserId();
        String role = mainPageService.getUserRole();

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "main";
    }
}
