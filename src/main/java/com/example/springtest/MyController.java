package com.example.springtest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/myPage")
    public String userMyPage() {
        return "myPage";
    }

    @GetMapping("/admin/myPage")
    public String adminMyPage() {
        return "myPage";
    }
}
