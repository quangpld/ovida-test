package io.ovida.test;

import io.ovida.test.controller.UserController;
import io.ovida.test.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService service;

    @MockBean
    private UserDetails userDetails;

    @Test
    void unauthenticatedGetMeRequestShouldReturnNullFromService() throws Exception {
        when(service.getMe(userDetails)).thenReturn(null);
        this.mockMvc.perform(get("/me")).andExpect(status().isUnauthorized());
    }
}
