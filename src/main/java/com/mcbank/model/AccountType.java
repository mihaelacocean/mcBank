package com.mcbank.model;

public enum AccountType {

  SAVINGS(1), CURRENT_ACCOUNT(2), DEBIT(3), CREDIT(4);

  private int accountType;

  AccountType(int accountType) {
    this.accountType = accountType;
  }

  public int getAccountType() {
    return accountType;
  }
}
