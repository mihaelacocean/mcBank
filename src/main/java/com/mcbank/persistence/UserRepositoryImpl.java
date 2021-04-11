package com.mcbank.persistence;

import com.mcbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepositoryImpl extends JpaRepository<User, Long>, UserRepository {

}
