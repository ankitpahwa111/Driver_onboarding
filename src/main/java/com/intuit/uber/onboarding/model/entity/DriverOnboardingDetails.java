/*-Author------------------------------------
*- bibhas.abhishek@gmail.com
*- uber-onboarding: DriverOnboardingDetails
*- 26 Nov 2021 8:51 PM
---Made with <3 in Delhi,India---------------
---Details-----------------------------------
*- Links:
-------------------------------------------*/

package com.intuit.uber.onboarding.model.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.intuit.uber.onboarding.model.enums.ProcessState;

@Entity
@DynamicUpdate
@Table(name = "driver_onboarding_details")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverOnboardingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "document_collection")
    private ProcessState documentCollection;

    @Column(name = "background_check")
    private ProcessState backgroundCheck;

    @Column(name = "tracking_device")
    private ProcessState trackingDevice;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
