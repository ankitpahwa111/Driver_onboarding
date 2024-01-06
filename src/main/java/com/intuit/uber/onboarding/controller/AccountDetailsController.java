/*-Author------------------------------------
*- bibhas.abhishek@gmail.com
*- uber-onboarding: SignupController
*- 26 Nov 2021 12:01 AM
---Made with <3 in Delhi,India---------------
---Details-----------------------------------
*- Links:
-------------------------------------------*/

package com.intuit.uber.onboarding.controller;

import com.google.gson.Gson;
import com.intuit.uber.onboarding.exception.AccountException;
import com.intuit.uber.onboarding.exception.UserException;
import com.intuit.uber.onboarding.factory.CustomResponseEntityFactory;
import com.intuit.uber.onboarding.model.entity.DriverStatus;
import com.intuit.uber.onboarding.service.producer.DriverStatusProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intuit.uber.onboarding.model.entity.AccountDetails;
import com.intuit.uber.onboarding.model.entity.CustomResponseEntity;
import com.intuit.uber.onboarding.service.AccountDetailsService;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountDetailsController {

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Autowired
    private DriverStatusProducer driverStatusProducer;

    @Autowired
    private CustomResponseEntityFactory customResponseEntityFactory;

    @PutMapping("/update/{id}")
    public CustomResponseEntity updateAccountStatus(@PathVariable Long id,
                                                        @RequestBody AccountDetails details) {
        log.info("Request for Update Account Status details for userId - {}", id);
        try {
            AccountDetails dbDetails = accountDetailsService.updateAccountDetails(id, details);
            DriverStatus driverStatus = new DriverStatus();
            driverStatus.setUserId(dbDetails.getUser().getId());
            driverStatus.setIsOnline(dbDetails.getIsOnline());
            driverStatusProducer.sendMessageToTopic(new Gson().toJson(driverStatus));
            return customResponseEntityFactory.getSuccessResponse(dbDetails);
        } catch (UserException | AccountException customException) {
            return customResponseEntityFactory.getBadRequestResponse(customException.getMessage());
        } catch (Exception exception) {
            return customResponseEntityFactory.getISEResponse();
        }
    }
}
