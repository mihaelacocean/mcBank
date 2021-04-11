package com.mcbank.controller;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.mcbank.model.Account;
import com.mcbank.model.User;
import com.mcbank.service.AccountService;
import com.mcbank.service.TransactionService;
import com.mcbank.service.UserService;
import com.mcbank.service.exception.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = com.mcbank.controller.UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private AccountService accountService;

  @MockBean
  private TransactionService transactionService;


  @Before
  public void mockServices() throws  Exception {
    when(userService.getById(anyLong())).thenReturn(getMockedUser(1l));
    when(accountService.openCurrentAccount(anyLong(), anyDouble()))
        .thenReturn(getMockedAccount(1L));
    when(userService.userExists(Mockito.anyLong())).thenReturn(true);
    doNothing().when(transactionService).doTransaction(anyLong(), anyDouble());
  }


  @Test
  public void testCreateAccount() throws Exception {

    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
        "/accounts/6/").accept(
        MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    Assert.assertEquals("1", result.getResponse().getContentAsString());
  }

  @Test
  public void test() throws Exception {

    mockUserService(1L);
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/users/1").accept(
        MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    String expectedResult = "{\"id\":1,\"name\":\"Name1\",\"surname\":\"Surname1\",\"balance\":0.0,\"accounts\":null}";
    Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
  }

  private void mockUserService(Long id) {
    Mockito.when(userService.getById(id)).thenReturn(getMockedUser(id));
  }

  private User getMockedUser(Long id) {
    User user = new User();
    user.setName("Name" + id);
    user.setSurname("Surname" + id);
    user.setId(id);
    return user;
  }


  private Account getMockedAccount( Long accountId) {
    Account account = new Account();
    account.setId(accountId);
    return account;
  }

}
