package com.bangladesh20.backend.Entity;

import com.bangladesh20.backend.Entity.Type.DonationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name="donation")
@Data
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private DonationStatus status;

    // Logged-in user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") //Create a column name user_id as foreign key in this donation table. it will take id from Users table
    private Users user;

    // Guest fields
    private String guestName;
    private String guestPhone;

    private String userPhone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_method_id",nullable = false)
    private paymentMethod method;

    private ZonedDateTime dateAndTime;
}



