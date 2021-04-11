package com.mcbank.controller;

import com.mcbank.model.Account;
import com.mcbank.model.User;
import com.mcbank.service.AccountService;
import com.mcbank.service.TransactionService;
import com.mcbank.service.UserService;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  private AccountService accountService;
  @Autowired
  private TransactionService transactionService;

  @GetMapping("/{id}")
  User getById(@PathVariable("id") Long id) {
    return userService.getById(id);
  }

  @PostMapping("{costumerId}/accounts/")
  public long createAccount(@PathVariable("costumerId") Long costumerId, Double initialCredit)
      throws Exception {

    if (!userService.userExists(costumerId)) {
      throw new IllegalArgumentException("Customer ID doesn't exist");
    }

    Account createdAccount = openAccount(costumerId, initialCredit != null ? initialCredit : 0);
    return createdAccount.getId();

  }


  @Transactional
  Account openAccount(Long userId, Double initialCredit) throws Exception {
    Account createdAccount = accountService.openCurrentAccount(userId, initialCredit);
    if(initialCredit != null && initialCredit > 0) {
      transactionService.doTransaction(createdAccount.getId(), initialCredit);
    }
    return createdAccount;
  }

}