package com.example.actuatorservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("name")
public class NameController {
  @GetMapping("full")
  public String full() {
    return "Ken Thompson";
  }
}
