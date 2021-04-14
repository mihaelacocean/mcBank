package com.mcbank.service;


import com.mcbank.model.Account;
import com.mcbank.model.AccountType;
import com.mcbank.persistence.AccountRepository;
import com.mcbank.service.exception.ValidationException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

  @Autowired
  AccountRepository accountRespository;

  @Override
  public Account openCurrentAccount(Long userId, Double initialCredit) throws ValidationException {
    if (initialCredit < 0) {
      throw new ValidationException("Initial credit cannot be negative");
    }
    if (userHasCurrentAccount(userId)) {
      throw new ValidationException("Client already has a current account open");
    }
    Account account = new Account();
    account.setUserId(userId);
    account.setType(AccountType.CURRENT_ACCOUNT);
    account.setCreationDate(new Date());
    account.setAmount(initialCredit);
    return accountRespository.save(account);
  }

  private boolean userHasCurrentAccount(Long userId) {
    return accountRespository.findByUserIdAndType(userId, AccountType.CURRENT_ACCOUNT).size() > 0;
  }

}
