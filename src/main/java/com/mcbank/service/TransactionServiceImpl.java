package com.mcbank.service;

import com.mcbank.model.Transaction;
import com.mcbank.model.TransactionType;
import com.mcbank.persistence.TransactionRespository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  TransactionRespository transactionRespository;

  @Override
  public void doTransaction(Long accountId, Double initialCredit) {
    Transaction transaction = new Transaction();
    transaction.setAmount(initialCredit);
    transaction.setAccountId(accountId);
    transaction.setTransactionDate(new Date());
    transaction.setType(TransactionType.ADDING);
    transactionRespository.save(transaction);

  }
}
