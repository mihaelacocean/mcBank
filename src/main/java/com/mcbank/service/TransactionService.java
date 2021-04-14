package com.mcbank.service;

import com.mcbank.model.Transaction;
import java.util.List;

public interface TransactionService {

  void addMoney(Long id, Double initialCredit);
}
