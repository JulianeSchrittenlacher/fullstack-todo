package org.example.recapspring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;

@SpringBootTest
@AutoConfigureMockMvc

class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DirtiesContext
    void authControllerGetMeWithLoggedIn() throws Exception {
        mockMvc.perform(get("/api/auth/me")
                                //Simple alternative with oauth2Login
//                        .with(oauth2Login()
//                                .attributes(a -> {
//                                    a.put("sub", "user-id");
//                                    a.put("login", "testUser");
//                                    a.put("avatar_url", "image-url");
//                                }))
                                .with(oidcLogin()
                                        .idToken(i -> i.subject("user-id"))
                                        .userInfoToken(token -> token
                                                .claim("login", "testUser")
                                                .claim("avatar_url", "image-url")
                                        ))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("user-id"));
    }


    @Test
    @DirtiesContext
    void getMe_returnsUnauthorized_withNoLoggedInUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


}