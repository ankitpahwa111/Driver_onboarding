package com.intuit.uber.onboarding;

import com.intuit.uber.onboarding.repository.UserRepository;
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
    }
}
