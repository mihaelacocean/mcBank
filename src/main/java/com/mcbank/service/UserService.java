package com.mcbank.service;

import com.mcbank.model.Credentials;
import com.mcbank.model.User;
import com.mcbank.service.exception.UserNotFoundException;

public interface UserService {

  User getById(Long id) throws UserNotFoundException;

  boolean userExists(Long id);

  String login(Credentials credentials) throws UserNotFoundException;

  User authenticateUser(String authorizationHeader);
}
