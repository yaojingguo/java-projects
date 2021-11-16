package com.example.testingweb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.google.common.truth.Truth.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JUnit4Test {

  @Autowired private HomeController controller;

  @Autowired private MyBean myBean;

  @Test
  public void contextLoads() throws Exception {
    assertThat(controller).isNotNull();
    assertThat(myBean).isNotNull();
  }
}
