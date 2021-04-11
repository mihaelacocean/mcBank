package com.mcbank.service;

import com.mcbank.service.exception.ValidationException;
import org.junit.Test;

public class AccountServiceTest {

  AccountServiceImpl accountService = new AccountServiceImpl();

  @Test(expected = ValidationException.class)
  public void testOpenAccount() throws Exception {
    accountService.openCurrentAccount(1L, -500.0);
  }

}
