package com.Contacts.service;

import com.Contacts.Controllers.ControllerUtils;
import com.Contacts.db.Contact;
import com.Contacts.db.Repository.ContactRepo;
import com.Contacts.db.Repository.UserRepo;
import com.Contacts.db.Role;
import com.Contacts.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {


    private final ContactRepo contactRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder,ContactRepo contactRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder=passwordEncoder;
        this.contactRepo=contactRepo;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
    public boolean addUser(User user) {

            User userFromDb = userRepo.findByUsername(user.getUsername());

            if (userFromDb != null) {
                return false;
            }

            user.setActive(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);

        return true;
    }
    public boolean isAuthor(Contact contact,String username) {
        if (username.equals(contact.getAuthor().getUsername())) {
            return true;
        }
        return false;
    }
}
