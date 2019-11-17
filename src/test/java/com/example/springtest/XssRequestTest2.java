package com.example.springtest;

import com.example.springtest.annotaion.WithRoleUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithRoleUser
public class XssRequestTest2 {

    @Autowired
    private MockMvc mvc;

    @Test
    public void given_request_xxsPage_with_formData_expect_success_with200OK() throws Exception {
        mvc.perform(get("/xss/form")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("contents", "test"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void given_request_xxsPage_with_jsonData_expect_success_with200OK() throws Exception {
        mvc.perform(get("/xss/json")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"contents\" : \"test\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
