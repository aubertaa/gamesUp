package com.gamesUP.gamesUP.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")  // Activate the "dev" profile for these tests
@AutoConfigureMockMvc
public class SecurityConfigTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPublicEndpoint() throws Exception {
        // Test access to a public endpoint
        mockMvc.perform(get("/api/public/games"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "client") // Mock user with the role "client"
    @Test
    public void testClientEndpoint() throws Exception {
        // Test access to client API endpoint
        mockMvc.perform(get("/api/client/games/search/name?nom=Portal"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "administrateur") // Mock user with the role "administrateur"
    @Test
    public void testAdminEndpoint() throws Exception {
        // Test access to admin API endpoint
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk());
    }

    @WithMockUser // Default Mock User with role "USER"
    @Test
    public void testUnauthorizedAccess() throws Exception {
        // Test access to admin API by unauthorized user
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());
    }

}
