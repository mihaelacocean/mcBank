package com.mcbank.controller;

import com.mcbank.exception.InvalidRequestException;
import com.mcbank.model.Account;
import com.mcbank.model.User;
import com.mcbank.service.AccountService;
import com.mcbank.service.TransactionService;
import com.mcbank.service.UserService;
import com.mcbank.service.exception.ValidationException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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


  @PostMapping("{costumerId}/accounts/current")
  public long createAccount(@PathVariable("costumerId") Long costumerId, @RequestBody Double initialCredit) {
    if (!userService.userExists(costumerId)) {
      throw new InvalidRequestException("Customer ID doesn't exist");
    }
    try {
      Account createdAccount = openAccount(costumerId, initialCredit != null ? initialCredit : 0);
      return createdAccount.getId();
    } catch ( ValidationException e) {
      throw  new InvalidRequestException(e.getMessage());
    }
  }

  @Transactional
  Account openAccount(Long userId, Double initialCredit) throws ValidationException {
    Account createdAccount = accountService.openCurrentAccount(userId, initialCredit);
    if(initialCredit != null && initialCredit > 0) {
      transactionService.addMoney(createdAccount.getId(), initialCredit);
    }
    return createdAccount;
  }

}