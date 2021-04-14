package com.mcbank.service;

import com.mcbank.model.Account;
import com.mcbank.service.exception.ValidationException;

public interface AccountService {

  Account openCurrentAccount(Long userId, Double initialCredit) throws ValidationException;

}

