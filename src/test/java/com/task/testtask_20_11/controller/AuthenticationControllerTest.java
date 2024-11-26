package com.task.testtask_20_11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.testtask_20_11.security.dto.AuthenticationRequest;
import com.task.testtask_20_11.security.dto.JwtAuthenticationResponse;
import com.task.testtask_20_11.security.dto.RegistrationRequest;
import com.task.testtask_20_11.security.entity.Role;
import com.task.testtask_20_11.security.entity.User;
import com.task.testtask_20_11.security.service.AuthenticationService;
import com.task.testtask_20_11.security.service.JwtService;
import com.task.testtask_20_11.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Authentication Controller testing")
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;
    private User user;
    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        User user = new User();
        user.setId(1L);
        user.setEmail("max@mail.ru");
        user.setRole(Role.ROLE_USER);
        user.setPassword("my_password132");
        this.user = user;
    }

    @Test
    public void registerShouldReturnJwtAuthenticationResponseWithProperToken() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail(this.user.getEmail());
        registrationRequest.setPassword(this.user.getPassword());

        String tokenExample= "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjoxLCJlbWFpbCI6Im1heEBtYWlsLnJ1Iiwic3ViIjoibWF4QG1haWwucnUiLCJpYXQiOjE3MzI2MTI5NjgsImV4cCI6MTczMzgyMjU2OH0.3uOGHpv9lRVlu4tAiSSESETbsSovW3jkx0htxPj9588";
        Mockito.when(authenticationService.register(registrationRequest)).thenReturn(new JwtAuthenticationResponse(
                tokenExample
        ));
        MvcResult result = this.mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(registrationRequest)))
                .andExpect(status().isOk()).andReturn();
        JwtAuthenticationResponse jwtAuthenticationResponse = objectMapper.readValue(result.getResponse().getContentAsString(), JwtAuthenticationResponse.class);

        assertNotNull(jwtAuthenticationResponse);
        assertEquals(tokenExample, jwtAuthenticationResponse.getToken());
    }

    @Test
    public void loginShouldReturnJwtAuthenticationResponseWithProperToken() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail(this.user.getEmail());
        authenticationRequest.setPassword(this.user.getPassword());

        String tokenExample= "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9BRE1JTiIsImlkIjoxLCJlbWFpbCI6Im1heEBtYWlsLnJ1Iiwic3ViIjoibWF4QG1haWwucnUiLCJpYXQiOjE3MzI2MTI5NjgsImV4cCI6MTczMzgyMjU2OH0.3uOGHpv9lRVlu4tAiSSESETbsSovW3jkx0htxPj9588";
        Mockito.when(authenticationService.login(authenticationRequest)).thenReturn(new JwtAuthenticationResponse(
                tokenExample
        ));
        MvcResult result = this.mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk()).andReturn();

        JwtAuthenticationResponse jwtAuthenticationResponse = objectMapper.readValue(result.getResponse().getContentAsString(), JwtAuthenticationResponse.class);

        assertNotNull(jwtAuthenticationResponse);
        assertEquals(tokenExample, jwtAuthenticationResponse.getToken());
    }

}
