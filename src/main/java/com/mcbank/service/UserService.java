package com.mcbank.service;

import com.mcbank.model.User;

public interface UserService {

  User getById(Long id);

  boolean userExists(Long id);
}
