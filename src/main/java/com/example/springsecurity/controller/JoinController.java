package com.example.springsecurity.controller;

import com.example.springsecurity.domain.JoinDTO;
import com.example.springsecurity.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO) {
        log.info("join user : {}", joinDTO.getUsername());
        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }
}
