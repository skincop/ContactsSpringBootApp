package com.Contacts.Configs.Utils;

import com.Contacts.Configs.Utils.CustomUserDetails;
import com.Contacts.db.Repository.UserRepo;
import com.Contacts.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepo.findByUsername(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}
