package com.example.demo1.controller;

import com.example.demo1.entity.Role;
import com.example.demo1.security.AccessCard;
import com.example.demo1.service.CardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CardRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @MockBean
    private AccessCard accessCard;




    @Test
    void beforeEach() {
        List<GrantedAuthority> roles = Arrays.asList(Role.ADMIN, Role.USER);
        User testUser = new User("test@gmail.com", "test", roles);
        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), roles);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @WithMockUser(username = "test@gmail.com", authorities = {"ADMIN"})
    public void testFindAllCardsWithAdminRole() throws Exception {
        mockMvc.perform(get("/api/v1/cards/allCards"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@gmail.com", authorities = {"USER"})
    public void testFindAllCardsWithUserRole() throws Exception {
        mockMvc.perform(get("/api/v1/cards/allCards"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testFindAllCardsWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/cards/allCards"))
                .andExpect(status().isUnauthorized());
    }

}