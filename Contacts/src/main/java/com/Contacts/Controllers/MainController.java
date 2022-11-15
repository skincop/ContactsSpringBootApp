package com.Contacts.Controllers;

import com.Contacts.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainController {
    @Autowired

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "main";
    }


}