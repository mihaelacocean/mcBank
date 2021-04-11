package com.mcbank.service;

import com.mcbank.model.Account;
import com.mcbank.model.User;
import com.mcbank.persistence.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Override
  public User getById(Long id) {

    User user = Optional.ofNullable(userRepository.findById(id)).get()
        .orElseThrow(() -> new IllegalArgumentException());
    calculateFinalBalance(user);
    return user;
  }

  void calculateFinalBalance(User user) {
    double balance = 0;
    for (Account account : user.getAccounts()) {
      balance += account.getAmount();
    }
    user.setBalance(balance);
  }
  
}
