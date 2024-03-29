/*-Author------------------------------------
*- bibhas.abhishek@gmail.com
*- uber-onboarding: DriverOnboardingService
*- 26 Nov 2021 9:02 PM
---Made with <3 in Delhi,India---------------
---Details-----------------------------------
*- Links:
-------------------------------------------*/

package com.intuit.uber.onboarding.service;

import com.intuit.uber.onboarding.exception.OnboardingException;
import com.intuit.uber.onboarding.exception.UserException;
import org.springframework.stereotype.Component;

import com.intuit.uber.onboarding.model.entity.DriverOnboardingDetails;
import com.intuit.uber.onboarding.model.entity.User;

@Component
public interface DriverOnboardingService {

    void initOnboarding(User user);

    void initBGC(User user);

    void initDeviceShipment(User user);

    void initDocumentCollection(User user);

    DriverOnboardingDetails updateOnboarding(Long id,
                                             DriverOnboardingDetails details) throws OnboardingException, UserException;
}
