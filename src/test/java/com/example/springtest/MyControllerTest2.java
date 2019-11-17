package com.example.springtest;

import com.example.springtest.annotaion.WithRoleAdmin;
import com.example.springtest.annotaion.WithRoleUser;
import com.example.springtest.annotaion.WithRoleUserAndAdmin;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MyControllerTest2 {

    private final String LOGIN_PAGE_URI = "http://localhost/login";

    @Autowired
    private MockMvc mvc;

    @Test
    @WithRoleUser
    public void given_requestMyPage_withAccountUserAnnotation_expect_viewNameIsMyPage() throws Exception {
        mvc.perform(get("/myPage"))
                .andExpect(view().name("myPage"));
    }

    @Test
    @WithRoleAdmin
    public void given_requestAdminPage_withRoleADMIN_expect_viewNameIsMyPage() throws Exception {
        mvc.perform(get("/admin/myPage"))
                .andExpect(view().name("myPage"));
    }

    @Test
    @WithRoleUserAndAdmin
    public void given_requestAdminPage_withRoleBoth_expect_viewNameIsMyPage() throws Exception {
        mvc.perform(get("/admin/myPage"))
                .andExpect(view().name("myPage"));
    }
}