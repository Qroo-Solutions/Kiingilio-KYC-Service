package com.qroo.kyc.data.vo;

import com.qroo.common.data.constants.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kiingilio_users")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String uid;
    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    private String idType;

    private String idNumber;

    @Column(nullable = false)
    private Gender gender=Gender.UNDISCLOSED;

    @Column(nullable = false, length = 20, unique = true)
    private String mobileNumber;

    @Column(length = 20, unique = true)
    private String whatsappNumber;

    @Column(nullable = false)
    private Byte verifiedMobileNumber=0;

    private boolean isEmailVerified;
    private String issuer;

    private String nationality;

    private String address;

    private String region;

    private String emailAddress;

    private Date dateOfBirth;

    private String profilePicture;

    private Float latitude;

    private Float longitude;

    private String language;

    private String instagram;
    private String facebook;
    private String google;
    private String twitter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization", nullable = true)
    private Organization organization;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate = new Date();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedDate = new Date();

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivatedDate = null;

    @Column(nullable = false, columnDefinition = "varchar(20)")
    private Status status=Status.INACTIVE;
}