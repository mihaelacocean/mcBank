package com.mcbank.persistence;

import com.mcbank.model.Account;
import com.mcbank.model.AccountType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositoryImpl extends JpaRepository<Account, Long>, AccountRepository {

  List<Account> findByUserIdAndType(Long userId, AccountType type);

}
