package com.Contacts.Controllers;

import com.Contacts.Configs.jwt.JwtProvider;
import com.Contacts.db.Repository.UserRepo;
import com.Contacts.db.Role;
import com.Contacts.db.User;
import com.Contacts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/auth")
    public String auth(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        User userEntity = userRepo.findByUsername(username);
        if (userEntity != null) {
            String token = jwtProvider.generateToken(userEntity.getUsername());
            Cookie cookie = new Cookie("Authorization", token);
            cookie.setMaxAge(15*24*60*60);
            response.addCookie(cookie);
            return "redirect:/contacts";
        }
        else {
            return "login";
        }
    }


    @PostMapping("/registration")
    public String addUser(@Valid User user,
                          BindingResult bindingResult,
                          Model model,
                          HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            Map<String,String> errorsMap= ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("user",user);
//            request.setAttribute("login",user.getUsername());
//            request.setAttribute("password",user.getPassword()) ;
            return "registration";
        }
        else {
            if (userService.addUser(user)) {
                model.addAttribute("message", "User exists!");
                return "login";
            }
            return "registration";
        }

    }

}
