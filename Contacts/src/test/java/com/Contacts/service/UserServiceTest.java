package com.Contacts.service;

import com.Contacts.db.Contact;
import com.Contacts.db.Repository.ContactRepo;
import com.Contacts.db.Repository.UserRepo;
import com.Contacts.db.Role;
import com.Contacts.db.User;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactRepo contactRepo;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private PasswordEncoder passwordEncoder;


    @Test
    public void addUser() {
        User user = new User();

        boolean isUserCreated = userService.addUser(user);

        Assert.assertTrue(isUserCreated);
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }
    @Test
    public void addUserFailTest() {
        User user = new User();

        user.setUsername("John");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("John");

        boolean isUserCreated = userService.addUser(user);

        Assert.assertFalse(isUserCreated);

        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }

    @Test
    public void isAuthor() {
        Contact contact = contactRepo.findAll().iterator().next();
        boolean isAuthor = userService.isAuthor(contact,contact.getAuthor().getUsername());

        Assert.assertTrue(isAuthor);
    }
    @Test
    public void isAuthorFailTest() {
        Contact contact = contactRepo.findAll().iterator().next();
        boolean isAuthor = userService.isAuthor(contact,contact.getAuthor().getUsername()+"FALSE");

        Assert.assertFalse(isAuthor);
    }
}