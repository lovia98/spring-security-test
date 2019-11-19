package com.example.springtest.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class LoggingTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void given_LoginWithCorrectUser_expectSuccess() throws Exception {
        mvc.perform(formLogin().user("user").password("password"))
                .andExpect(authenticated());
    }

    @Test
    public void given_LoginWithCorrectUser_expectFail() throws Exception {
        mvc.perform(formLogin().user("someone").password("password"))
                .andExpect(unauthenticated());
    }
}
