package com.navvish.germanphonesparser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ping {

    @GetMapping(path = "/")
    public String imUpAndRunning() {
        return "{healthy:true}";
    }



}
