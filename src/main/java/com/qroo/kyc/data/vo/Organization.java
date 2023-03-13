package com.qroo.kyc.data.vo;

import com.qroo.common.data.constants.Status;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import jakarta.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kiingilio_organizations")
public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, length = 20)
    private String mobileNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false, updatable = false)
    private User user;
    private String facebook;
    private String google;
    private String twitter;
    @Column(length = 20)
    private String whatsapp;
    private String instagram;
    private String emailAddress;
    private String logo;
    private String banner;
    private BigDecimal kiingilioRateAmount;
    private Double kiingilioRatePercentage;
    @Column(nullable = false, updatable = false)
    private String alias;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate = new Date();
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedDate = new Date();
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private Status status = Status.INACTIVE;
}