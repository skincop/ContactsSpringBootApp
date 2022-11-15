package com.Contacts.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("LOGOUT");
        Cookie[] cookies=request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization") || cookie.getName().equals("User")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
}
