package com.mcbank.persistence;

import com.mcbank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositoryImpl extends JpaRepository<Account, Long>, AccountRepository {

}
