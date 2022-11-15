package com.Contacts.Controllers;

import com.Contacts.db.Contact;
import com.Contacts.db.Repository.ContactRepo;
import com.Contacts.db.Repository.UserRepo;
import com.Contacts.db.Role;
import com.Contacts.db.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class AdminController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ContactRepo contactRepo;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);

        return "redirect:/user";
    }
    @GetMapping("/info/{user}")
    public String userShowContacts(@PathVariable User user, Model model, @PageableDefault(sort = {"id"} ,direction = Sort.Direction.ASC) Pageable pageable) {
        Iterable<Contact> contacts=contactRepo.findByAuthor(user,pageable);
        model.addAttribute("contacts",contacts);
        model.addAttribute("userId",user.getId());
        model.addAttribute("url","/user/info/"+user.getId());
        return "userContacts";
    }


}
