package com.example.authenticationspring.controller;

import com.example.authenticationspring.entity.UserEntity;
import com.example.authenticationspring.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/home")
    public String getHome(Model model){
        final String email = SecurityContextHolder.getContext().getAuthentication().getName();
        SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
        UserEntity user = userService.findByEmail(email).get();
        model.addAttribute("email", email);
        model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("dob", user.getDob().toString());
        model.addAttribute("userId", userService.findByEmail(email).get().getUserId());
        return "home";
    }
}
