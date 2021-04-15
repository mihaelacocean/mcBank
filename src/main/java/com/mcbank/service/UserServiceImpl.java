package com.mcbank.service;

import com.mcbank.exception.AuthenticationException;
import com.mcbank.model.Account;
import com.mcbank.model.Credentials;
import com.mcbank.model.User;
import com.mcbank.persistence.UserRepository;
import com.mcbank.service.exception.UserNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Override
  public User getById(Long id) throws UserNotFoundException {

    User user = Optional.ofNullable(userRepository.findById(id)).get()
        .orElseThrow(() -> new UserNotFoundException("User ID not found"));
    calculateFinalBalance(user);
    return user;
  }

  @Override
  public boolean userExists(Long id) {
    return userRepository.existsById(id);
  }

  @Override
  public String login(Credentials credentials) throws UserNotFoundException {
    List<User> users = userRepository
        .findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());
    if (users.isEmpty()) {
      throw new UserNotFoundException("Invalid credentials");

    }
    return users.get(0).getAuthorization();
  }

  @Override
  public User authenticateUser(String authorizationHeader)  {
    List<User> users = userRepository.findByAuthorization(authorizationHeader);
    if (users.isEmpty()) {
      throw new AuthenticationException("Invalid user");
    } else {
      return users.get(0);
    }
  }

  void calculateFinalBalance(User user) {
    double balance = 0;
    for (Account account : user.getAccounts()) {
      balance += account.getAmount();
    }
    user.setBalance(balance);
  }

}
