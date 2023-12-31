package com.intuit.uber.onboarding.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DriverStatus {
    Long userId;
    Boolean isOnline;
}
