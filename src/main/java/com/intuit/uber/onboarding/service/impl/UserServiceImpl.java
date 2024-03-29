/*-Author------------------------------------
*- bibhas.abhishek@gmail.com
*- uber-onboarding: SignupService
*- 26 Nov 2021 12:28 AM
---Made with <3 in Delhi,India---------------
---Details-----------------------------------
*- Links:
-------------------------------------------*/

package com.intuit.uber.onboarding.service.impl;

import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.intuit.uber.onboarding.exception.UserException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.uber.onboarding.model.entity.User;
import com.intuit.uber.onboarding.model.enums.IdentityType;
import com.intuit.uber.onboarding.model.enums.UserType;
import com.intuit.uber.onboarding.repository.UserRepository;
import com.intuit.uber.onboarding.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User userSignupService(User user) throws UserException {

        if (Objects.nonNull(userRepository.findByContact(user.getContact()))) {
            throw new UserException("User with Contact number - " + user.getContact() + " Already Exists");
        }
        if (user.getUserType().equals(UserType.DRIVER)
                && !user.getIdentityType().equals(IdentityType.DRIVING_LICENCE)) {
            throw new UserException("Driver needs a driving licence as id proof");
        }

        if (user.getAge() < 18) {
            throw new UserException("User is below the legal age to drive");
        }
        // saving the encrypted version
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }
}
