/*-Author------------------------------------
*- bibhas.abhishek@gmail.com
*- uber-onboarding: SignupController
*- 26 Nov 2021 12:01 AM
---Made with <3 in Delhi,India---------------
---Details-----------------------------------
*- Links:
-------------------------------------------*/

package com.intuit.uber.onboarding.controller;

import com.intuit.uber.onboarding.exception.OnboardingException;
import com.intuit.uber.onboarding.exception.UserException;
import com.intuit.uber.onboarding.factory.CustomResponseEntityFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.uber.onboarding.model.entity.CustomResponseEntity;
import com.intuit.uber.onboarding.model.entity.DriverOnboardingDetails;
import com.intuit.uber.onboarding.service.DriverOnboardingService;

@RestController
@RequestMapping("/api/onboarding")
@Slf4j
public class DriverOnboardingController {

    @Autowired
    private DriverOnboardingService driverOnboardingService;

    @Autowired
    private CustomResponseEntityFactory customResponseEntityFactory;

    @PutMapping("/update/{id}")
    public CustomResponseEntity updateOnboardingDetails(@PathVariable Long id,
                                                        @RequestBody DriverOnboardingDetails details) {
        log.info("Request for Update Onboarding details for userId - {}", id);
        try {
            DriverOnboardingDetails dbDetails = driverOnboardingService.updateOnboarding(id,
                details);
            return customResponseEntityFactory.getSuccessResponse(dbDetails);
        } catch (UserException | OnboardingException customException) {
            return customResponseEntityFactory.getBadRequestResponse(customException.getMessage());
        } catch (Exception exception) {
            return customResponseEntityFactory.getISEResponse();
        }
    }
}
