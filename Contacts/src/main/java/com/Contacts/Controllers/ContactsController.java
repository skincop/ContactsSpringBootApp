package com.Contacts.Controllers;

import com.Contacts.db.Contact;
import com.Contacts.db.Repository.ContactRepo;
import com.Contacts.db.Repository.UserRepo;
import com.Contacts.db.User;
import com.Contacts.service.MailSender;
import com.Contacts.service.UserService;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/contacts")
public class ContactsController {
    @Autowired
    private ContactRepo contactRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private UserService userService;

    @GetMapping
    public String GetAll(Authentication authentication,
                         @PageableDefault(sort = {"id"} ,direction = Sort.Direction.ASC) Pageable pageable,
                         Map<String, Object> model){
        User user = userRepo.findByUsername(authentication.getName());
        Page<Contact> contacts=contactRepo.findByAuthor(user,pageable);
        model.put("url","/contacts");
        model.put("contacts",contacts);
        return "contacts";
    }

    @GetMapping("/delete/{contact}")
    public String DeleteUser(Authentication authentication,
                             @PathVariable Contact contact,
                             @RequestParam(required = false, defaultValue = "") User user,
                             Model model) {

        if (contact != null) {
            if (!( userService.isAuthor(contact, authentication.getName()) ||authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
                System.out.println("AUTHOR FALSE");
                model.addAttribute("error", "You are trying to delete a contact that is not yours");
                return "error";
            } else {
                contactRepo.delete(contact);
                return user != null ? "redirect:/user/info/" + user.getId() : "redirect:/contacts";
            }
        }
        else {
            model.addAttribute("error", "Something went wrong");
            return "error";
        }

    }


    @GetMapping("/update/{contact}")
    public String UpdateUser(Authentication authentication,@PathVariable Contact contact,@RequestParam(required = false, defaultValue = "") User user,Model model) {
        if (contact != null) {
            if (!( userService.isAuthor(contact, authentication.getName()) ||authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
                model.addAttribute("error","You are trying to update a contact that is not yours");
                return "error";
            }
            model.addAttribute("contact", contact);
            if (user != null) {
                model.addAttribute("userId", user.getId());
            }
            return "editContact";
        }
        else {
            model.addAttribute("error", "Something went wrong");
            return "error";
        }

    }

    @PostMapping("/update/{contact}")
    public String Upp(Authentication authentication,
                      @PathVariable Contact contact,
//                      @RequestParam String firstName,
//                      @RequestParam String secondName,
//                      @RequestParam String email,
//                      @RequestParam String phone,
                      @RequestParam(required = false, defaultValue = "") User user,
                      @Valid Contact editContact,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            Map<String,String> errorsMap= ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute(contact);
            model.addAttribute("wrongContact",editContact);
            return "editContact";
        }
        if (!( userService.isAuthor(contact, authentication.getName()) ||authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
            model.addAttribute("error","You are trying to update a contact that is not yours");
            return "error";
        }
        contactRepo.save(contact.ReplaceAll(editContact.getFirstName(),editContact.getSecondName(),editContact.getEmail(),editContact.getPhoneNumber()));
        return user != null ? "redirect:/user/info/" + user.getId():"redirect:/contacts";

    }

    @PostMapping
    public String Add(Authentication authentication,
                      Pageable pageable,
                      @Valid Contact contact,
                      BindingResult bindingResult,
                      Model model) {
        System.out.println("ADD");
        if (bindingResult.hasErrors()) {
            System.out.println("AAAA");
            Map<String,String> errorsMap= ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute(contact);
            User user = userRepo.findByUsername(authentication.getName());
            Page<Contact> contacts=contactRepo.findByAuthor(user,pageable);
            model.addAttribute("contacts",contacts);
            model.addAttribute("url","/contacts");
            model.addAttribute("wrongContact",contact);
            return "contacts";
        }
        User user = userRepo.findByUsername(authentication.getName());
       if (contactRepo.findFirstByFirstNameAndSecondNameAndEmailAndPhoneNumberAndAuthor(contact.getFirstName(),
               contact.getSecondName(), contact.getEmail(), contact.getPhoneNumber(), user) != null) {
           model.addAttribute("error", "You trying to add same contact");
           return "error";
       }

       Contact newContact = new Contact(contact.getFirstName(),
               contact.getSecondName(), contact.getEmail(), contact.getPhoneNumber(), user);
       contactRepo.save(newContact);
       Iterable<Contact> contacts = contactRepo.findByAuthor(user,pageable);
       model.addAttribute("contacts", contacts);
        model.addAttribute("url","/contacts");
       return "contacts";
   }

    @GetMapping("/filter")
    public String filter(Authentication authentication,
                         @RequestParam(required = false, defaultValue = "") String filter,
                         @RequestParam(required = false, defaultValue = "") User selectedUser,
                         @PageableDefault(sort = {"id"} ,direction = Sort.Direction.ASC) Pageable pageable,
                         Map<String, Object> model) {
        User user = userRepo.findByUsername(authentication.getName());
        Page<Contact> messages;
        user = selectedUser == null ? user : selectedUser;
        if (filter != null && !filter.isEmpty()) {
            messages = contactRepo.findByFirstNameAndAuthor(filter, user,pageable);
        } else {
            messages = contactRepo.findByAuthor(user,pageable);
        }

        model.put("contacts", messages);
        model.put("url","/contacts");
        model.put("filter", filter);
        if (selectedUser!=null) {
            model.put("userId", selectedUser.getId());
        }

        return selectedUser != null ? "userContacts" : "contacts";
    }
    @GetMapping("/send/{contact}")
    public String sendPage(@PathVariable Contact contact,Map<String, Object> model){
        model.put("contactId",contact.getId());
        return "sendContact";
    }

    @PostMapping("/send")
    public String send(@RequestParam String email,Authentication authentication,@RequestParam Contact contact,Map<String, Object> model){
        try {
            User user = userRepo.findByUsername(authentication.getName());
            mailSender.send(email, "Contacts", "Hi! This contact was sent to you by "+ user.getUsername()
                    + "\nContact: " + contact.toString());
        }
        catch (Exception e) {
            model.put("error","Something going wrong");
            return "error";
        }
        return "redirect:/contacts";
    }

}
