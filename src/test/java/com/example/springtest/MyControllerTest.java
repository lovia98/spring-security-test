package com.example.springtest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MyControllerTest {

    private final String LOGIN_PAGE_URI = "http://localhost/login";

    @Autowired
    private MockMvc mvc;

    @Test
    public void given_requestMyPage_withAccountUser_expect_viewNameIsMyPage() throws Exception {
        mvc.perform(get("/myPage").with(user("user")))
                .andExpect(view().name("myPage"));
    }

    @Test
    public void given_requestRootPage_expect_redirectLoginPage() throws Exception {
        mvc.perform(get("/myPage").with(anonymous()))
                .andDo(print())
                .andExpect(redirectedUrl(LOGIN_PAGE_URI));
    }

    @Test
    @WithMockUser("user")
    public void given_requestMyPage_withAccountUserAnnotation_expect_viewNameIsMyPage() throws Exception {
        mvc.perform(get("/myPage"))
                .andExpect(view().name("myPage"));
    }

    @Test
    @WithMockUser(username = "mockUser", roles = "USER")
    public void given_requestMyPage_withRoleUSER_expect_viewNameIsMyPage() throws Exception {
        mvc.perform(get("/myPage"))
                .andExpect(view().name("myPage"));
    }

    @Test
    @WithMockUser(username = "mockUser", roles = "ADMIN")
    public void given_requestAdminPage_withRoleADMIN_expect_viewNameIsMyPage() throws Exception {
        mvc.perform(get("/admin/myPage"))
                .andExpect(view().name("myPage"));
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"USER", "ADMIN"})
    public void given_requestAdminPage_withRoleBoth_expect_viewNameIsMyPage() throws Exception {
        mvc.perform(get("/admin/myPage"))
                .andExpect(view().name("myPage"));
    }

    @Test
    @WithAnonymousUser
    public void given_requestRootPageAnnotaion_expect_redirectLoginPage() throws Exception {
        mvc.perform(get("/myPage"))
                .andDo(print())
                .andExpect(redirectedUrl(LOGIN_PAGE_URI));
    }
}