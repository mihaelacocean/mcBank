package com.mcbank.persistence;

import com.mcbank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepositoryImpl  extends JpaRepository<Transaction, Long>, TransactionRespository {

}
