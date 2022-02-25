package com.manning.readinglist.secure;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.manning.readinglist.Reader;
import com.manning.readinglist.ReadingListApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReadingListApplication.class)
@WebAppConfiguration
public class MockMvcWebTests {

  @Autowired
  WebApplicationContext webContext;
  private MockMvc mockMvc;//mockMvc取代实际的Servlet容器，测试运行相对较快

  @Before
  public void setupMockMvc() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(webContext)
        .apply(springSecurity())
        .build();
  }
//  @Test
  public void homePage_unauthenticatedUser() throws Exception {
    mockMvc.perform(get("/?username=liyang"))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string("Location", "http://localhost/login"));
  }
  @Test
  @WithUserDetails("walt")
//  @WithMockUser(username="walt", password="123456", roles="READER")//绕过对UserDetails查询，直接创建
  public void homePage_authenticatedUser() throws Exception {
    Reader expectedReader = new Reader();
    expectedReader.setUsername("walt");
    expectedReader.setPassword("$2a$10$PMnzNRlXbLhMWPlyboM5mO5iBczyR2AKOibvJb24uTb0wSE6P9UmK");
    expectedReader.setFullname("Walt Disney");
    
    mockMvc.perform(get("/?username=walt"))
        .andExpect(status().isOk())
        .andExpect(view().name("readingList"))
        .andExpect(model().attribute("reader", 
                           samePropertyValuesAs(expectedReader)))
        .andExpect(model().attribute("books", hasSize(0)));
//        .andExpect(model().attribute("amazonID", "habuma-20"));
    }

}
