package com.Contacts.Controllers.Data;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}