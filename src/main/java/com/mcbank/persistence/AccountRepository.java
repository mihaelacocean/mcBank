package com.mcbank.persistence;

import com.mcbank.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository {

  Account save(Account account);

}
