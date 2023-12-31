package com.intuit.uber.onboarding;

import com.intuit.uber.onboarding.model.entity.User;
import com.intuit.uber.onboarding.model.enums.IdentityType;
import com.intuit.uber.onboarding.model.enums.UserType;
import com.intuit.uber.onboarding.repository.UserRepository;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableCaching
public class UberOnboardingApplication {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(UberOnboardingApplication.class, args);
//        User user = new User();
//        user.setId(1L);
//        user.setPassword("123");
//        user.setAge(12);
//        user.setAddress("Aa");
//        user.setName("anklet");
//        user.setUserType(UserType.DRIVER);
//        user.setContact("23");
//        user.setIdentityType(IdentityType.AADHAR);
//        user.setIdentityNumber("12");
//        UserRepository userRepository = context.getBean(UserRepository.class);
//        userRepository.save(user);
    }
}
