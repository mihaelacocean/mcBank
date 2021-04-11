package com.mcbank.controller;

import com.mcbank.model.User;
import com.mcbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("/{id}")
  User getById(@PathVariable("id") Long id) {
    return userService.getById(id);
  }

}
