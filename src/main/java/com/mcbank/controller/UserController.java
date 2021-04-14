package com.mcbank.controller;

import com.mcbank.exception.AuthenticationException;
import com.mcbank.exception.AuthorizationException;
import com.mcbank.exception.InvalidRequestException;
import com.mcbank.model.Account;
import com.mcbank.model.Credentials;
import com.mcbank.model.User;
import com.mcbank.service.AccountService;
import com.mcbank.service.TransactionService;
import com.mcbank.service.UserService;
import com.mcbank.service.exception.UserNotFoundException;
import com.mcbank.service.exception.ValidationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api( value = "Provides functionality for user operations")
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  private AccountService accountService;
  @Autowired
  private TransactionService transactionService;

  @ApiOperation(value = "Get a customer by id. For non-admin users, only own customer details are visible")
  @GetMapping("/{id}")
  User getById(@PathVariable("id") Long id,
      @ApiParam(value = "Authorization token for identifying user") @RequestHeader("authorization") String authorization) {
    User user = userService.authenticateUser(authorization);
    if (!user.isAdmin() && !user.getId().equals(id)) {
      throw new AuthorizationException("Forbidden");
    }
    return userService.getById(id);
  }

  @ApiOperation(response = Long.class, responseReference = "authorization token for the logged in user", value = "Creates a new current account for an existing user. If initialCredit is valid, the amount is added in client's current account")
  @PostMapping("{costumerId}/accounts/current")
  public long createAccount(@PathVariable("costumerId") Long costumerId,
      @ApiParam(required = false) @RequestBody Double initialCredit,
      @ApiParam(value = "Authorization token for identifying user", required = true) @RequestHeader("authorization") String authorization) {
    User user = userService.authenticateUser(authorization);
    if (!user.isAdmin()) {
      throw new AuthorizationException("Forbidden");
    }
    if (!userService.userExists(costumerId)) {
      throw new InvalidRequestException("Customer ID doesn't exist");
    }
    try {
      Account createdAccount = openAccount(costumerId, initialCredit != null ? initialCredit : 0);
      return createdAccount.getId();
    } catch (ValidationException e) {
      throw new InvalidRequestException(e.getMessage());
    }
  }

  @ApiOperation(value = "Executes login method")
  @PostMapping("/login")
  public String login(@RequestBody Credentials credentials) {
    try {
      return userService.login(credentials);
    } catch (UserNotFoundException e) {
      throw new AuthenticationException(e.getMessage());
    }
  }


  @Transactional
  Account openAccount(Long userId, Double initialCredit) throws ValidationException {
    Account createdAccount = accountService.openCurrentAccount(userId, initialCredit);
    if (initialCredit != null && initialCredit > 0) {
      transactionService.addMoney(createdAccount.getId(), initialCredit);
    }
    return createdAccount;
  }

}