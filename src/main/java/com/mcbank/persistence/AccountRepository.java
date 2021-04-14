package com.mcbank.persistence;

import com.mcbank.model.Account;
import com.mcbank.model.AccountType;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository {

  Account save(Account account);

  List<Account> findByUserIdAndType(Long userId, AccountType type);

}
