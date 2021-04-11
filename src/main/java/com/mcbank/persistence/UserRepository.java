package com.mcbank.persistence;

import com.mcbank.model.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findById(Long id);

  boolean existsById(Long id);

}
