package com.example.authenticationspring.controller;


import com.example.authenticationspring.entity.UserEntity;
import com.example.authenticationspring.model.dto.UserDto;
import com.example.authenticationspring.service.EmailSenderService;
import com.example.authenticationspring.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }
    @GetMapping("/register")
    public String addUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "auth/register";
    }


    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") UserEntity user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "auth/register";
        }
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("mess", "Email đã tồn tại. Hãy nhập Email mới!");
            return "auth/register";
        }
        session.setAttribute("otp-register", otpCode());
        session.setMaxInactiveInterval(360);
        String subject = "Hello Here Is Your Code OTP!";
        String mess = "Hi You@" + " \nDear " + user.getFirstName() + " " + user.getLastName() + " " + "Here is your OTP Code: " + session.getAttribute("otp-register") + " Plaese input to form!" + "\n Thanks!";
        this.emailSenderService.sendEmail(user.getEmail(), subject, mess);

        session.setAttribute("userid", user.getUserId());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("firstname", user.getFirstName());
        session.setAttribute("lastname", user.getLastName());
        session.setAttribute("dob", user.getDob());
        session.setAttribute("phone", user.getPhone());
        session.setAttribute("gender", user.getGender());
        session.setAttribute("password", user.getPassword());
        return "redirect:/otp-check";
    }

    @RequestMapping(value = "re-send")
    public String resend(HttpSession session) {
        session.removeAttribute("otp-register");
        session.setAttribute("otp-register", otpCode());
        session.setMaxInactiveInterval(360);
        String subject = "Hello Here Is Your Code OTP!";
        String mess = "Hi You@" + " \nDear " + session.getAttribute("firstname") + " " + session.getAttribute("lastname") + " " + "Here is your OTP Code: " + session.getAttribute("otp-register") + " Please input to form!" + "\n Thanks!";
        this.emailSenderService.sendEmail((String) session.getAttribute("email"), subject, mess);
        return "redirect:/otp-check";
    }

    public String otpCode() {
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        return String.valueOf(code);
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto user = userService.findByMail(email);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/user/update")
    public String updateUser(@ModelAttribute(name = "user") UserDto userDto, @ModelAttribute(name = "confirm-new-password")String newPass) {
        userDto.setPassword(newPass);
        System.out.println(newPass);
        userService.updateProfile(userDto);
        return "redirect:/profile";
    }
}

