package com.qroo.kyc.data.vo;

import com.qroo.common.data.constants.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kiingilio_accounts")
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    private User user;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization", nullable = true)
    private Organization organization;
    @Column(nullable = false, length = 20)
    private String mobileNumber;
    private BigDecimal currentBalance;
    private BigDecimal totalSpent;
    private BigDecimal totalAccrued;
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