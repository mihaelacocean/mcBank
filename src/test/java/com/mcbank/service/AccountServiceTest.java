package com.mcbank.service;

import com.mcbank.model.Account;
import com.mcbank.model.AccountType;
import com.mcbank.persistence.AccountRepository;
import com.mcbank.service.exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

  @Mock
  AccountRepository accountRepository;

  @InjectMocks
  AccountServiceImpl accountService;

  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test()
  public void testOpenAccountNegativeSum() throws Exception {
    exceptionRule.expect(ValidationException.class);
    exceptionRule.expectMessage("Initial credit cannot be negative");

    accountService.openCurrentAccount(1L, -500.0);
  }

  @Test()
  public void testOpenCurrentAccountExisting() throws Exception {
    Mockito.when(accountRepository.findByUserIdAndType(1L, AccountType.CURRENT_ACCOUNT))
        .thenReturn(getCurrentAccount());

    exceptionRule.expect(ValidationException.class);
    exceptionRule.expectMessage("Client already has a current account open");

    accountService.openCurrentAccount(1L, 500.0);
  }

  private List<Account> getCurrentAccount() {
    List<Account> currentAccounts = new ArrayList<>();
    Account account = new Account();
    account.setId(1L);
    account.setUserId(1L);
    account.setType(AccountType.CURRENT_ACCOUNT);
    currentAccounts.add(account);
    return currentAccounts;
  }

}
