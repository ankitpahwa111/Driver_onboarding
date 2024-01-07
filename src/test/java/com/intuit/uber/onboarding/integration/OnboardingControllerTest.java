package com.intuit.uber.onboarding.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.uber.onboarding.model.entity.DriverOnboardingDetails;
import com.intuit.uber.onboarding.model.entity.User;
import com.intuit.uber.onboarding.model.enums.IdentityType;
import com.intuit.uber.onboarding.model.enums.ProcessState;
import com.intuit.uber.onboarding.model.enums.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OnboardingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    void mockSignUpUser() throws Exception {
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
    void testOnboardingDataUpdate_success() throws Exception {
        mockSignUpUser();
        DriverOnboardingDetails driverOnboardingDetails = DriverOnboardingDetails.builder()
                .documentCollection(ProcessState.COMPLETED)
                .backgroundCheck(ProcessState.IN_PROGRESS)
                .trackingDevice(ProcessState.INITIATED)
                .build();
        this.mockMvc.perform(
                        put("/api/onboarding/update/{id}", "1").contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(driverOnboardingDetails)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()));
    }

    @Test
    void testOnboardingDataUpdate_returnUserNotFound() throws Exception {
        DriverOnboardingDetails driverOnboardingDetails = DriverOnboardingDetails.builder()
                .documentCollection(ProcessState.COMPLETED)
                .backgroundCheck(ProcessState.IN_PROGRESS)
                .trackingDevice(ProcessState.INITIATED)
                .build();
        this.mockMvc.perform(
                        put("/api/onboarding/update/{id}", "0").contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(driverOnboardingDetails)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User details not found"));
    }
}
