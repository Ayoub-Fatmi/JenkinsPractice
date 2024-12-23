package com.example.jenkinstp.controller;

import com.example.jenkinstp.service.JenkinstpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class JenkinstpController {

    @Autowired
    private JenkinstpService jenkinstpService;

    @GetMapping("/add")
    public double add(@RequestParam double a, @RequestParam double b) {
        return jenkinstpService.add(a, b);
    }

    @GetMapping("/subtract")
    public double subtract(@RequestParam double a, @RequestParam double b) {
        return jenkinstpService.subtract(a, b);
    }

    @GetMapping("/multiply")
    public double multiply(@RequestParam double a, @RequestParam double b) {
        return jenkinstpService.multiply(a, b);
    }

    @GetMapping("/divide")
    public double divide(@RequestParam double a, @RequestParam double b) {
        return jenkinstpService.divide(a, b);
    }
}

