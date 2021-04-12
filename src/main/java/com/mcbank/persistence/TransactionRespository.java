package com.mcbank.persistence;

import com.mcbank.model.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRespository {

  Transaction save(Transaction transaction);
}
