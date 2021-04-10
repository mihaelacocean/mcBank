package com.mcbank.controller;

import com.mcbank.model.User;
import com.mcbank.service.UserService;
import org.junit.Assert;
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

}
