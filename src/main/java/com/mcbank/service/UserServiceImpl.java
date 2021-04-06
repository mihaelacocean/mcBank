package com.mcbank.service;

import com.mcbank.model.User;
import com.mcbank.persistence.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService{

  @Autowired
  UserRepository userRepository;

  public User getById(Long id) {
    return Optional.ofNullable(userRepository.findById(id)).get().orElseThrow(() ->new IllegalArgumentException());
  }


}
