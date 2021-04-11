package com.mcbank.service;

import com.mcbank.model.Account;
import com.mcbank.model.User;
import com.mcbank.persistence.UserRepository;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserServiceImpl userService;

  @Test
  public void testUserBalance() {
    Mockito.when(userRepository.findById(1L)).thenReturn(mockUser(1L));
    User user = userService.getById(1L);
    Assert.assertEquals(132.5, user.getBalance(), 2);

  }


  Optional<User> mockUser(Long id) {
    User user = new User();
    user.setId(id);
    Account currentAccount = new Account();
    currentAccount.setId(1L);
    currentAccount.setAmount(100);
    Account savings = new Account();
    savings.setId(2L);
    savings.setAmount(32.5);
    user.getAccounts().add(currentAccount);
    user.getAccounts().add(savings);
    return Optional.ofNullable(user);

  }
}
