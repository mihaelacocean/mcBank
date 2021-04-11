package com.mcbank.service;

import com.mcbank.model.Transaction;
import java.util.List;

public interface TransactionService {

  void doTransaction(Long id, Double initialCredit);
}
