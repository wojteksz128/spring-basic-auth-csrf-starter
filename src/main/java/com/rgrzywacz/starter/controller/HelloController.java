package com.rgrzywacz.starter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    @RequestMapping(method = GET)
    public String hello() {
        return "hello";
    }

}
