package com.example.springtest.annotaion;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "mockUser", roles = {"ADMIN", "USER"})
public @interface WithRoleUserAndAdmin {
}
