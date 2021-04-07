package com.mcbank.model;

public enum TransactionType {

  WITHDRAW(1), ADDING(2);

  private int transactionCode;

   TransactionType(int transactionCode) {
    this.transactionCode = transactionCode;
  }

  public int getTransactionCode() {
     return transactionCode;
  }


}
