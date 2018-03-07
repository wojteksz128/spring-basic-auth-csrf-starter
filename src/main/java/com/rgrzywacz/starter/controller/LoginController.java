package com.rgrzywacz.starter.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(method = POST)
    public Map<String, Object> login(Authentication auth) {
        return Collections.singletonMap("user", auth.getName());
    }
}

