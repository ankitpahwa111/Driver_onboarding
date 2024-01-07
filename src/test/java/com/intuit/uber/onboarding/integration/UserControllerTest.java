package com.intuit.uber.onboarding.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.uber.onboarding.model.entity.User;
import com.intuit.uber.onboarding.model.enums.IdentityType;
import com.intuit.uber.onboarding.model.enums.UserType;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(value = 1)
    void testNewUserSignup_success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = User.builder()
                .address("AA")
                .name("Test Driver")
                .age(40)
                .contact("9899429440")
                .password("Test@123")
                .userType(UserType.DRIVER)
                .identityType(IdentityType.DRIVING_LICENCE)
                .identityNumber("123456789012345")
                .build();
        this.mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(HttpStatus.CREATED.getReasonPhrase()));
    }

    @Test
    @Order(value = 4)
    void testNewUserSignup_returnException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = User.builder()
                .address("AA")
                .build();
        this.mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @Test
    @Order(value = 5)
    void testNewUserSignup_returnDriverValidationError() throws Exception {
        User user = User.builder()
                .address("AA")
                .name("Test Driver")
                .age(40)
                .contact("989942944")
                .password("Test@123")
                .userType(UserType.DRIVER)
                .identityType(IdentityType.AADHAR)
                .identityNumber("123456789012345")
                .build();
        this.mockMvc.perform(post("/api/user").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andDo(print())
                .andExpect(jsonPath("$.message").value("Driver needs a driving licence as id proof"));
    }

    @Test
    @Order(value = 2)
    void testUserGet_success() throws Exception {
        this.mockMvc.perform(get("/api/user/{id}", "1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()));
    }

    @Test
    @Order(value = 3)
    void testUserGet_returnNotFound() throws Exception {
        this.mockMvc.perform(get("/api/user/{id}", "2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(HttpStatus.NOT_FOUND.getReasonPhrase()));
    }
}
