package com.bangladesh20.backend.Dto.Donation;

import com.bangladesh20.backend.Entity.Type.DonationStatus;
import com.bangladesh20.backend.Entity.paymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class DonationResponseDto {
    private Long id;
    private String transactionId;
    private BigDecimal amount;
    private paymentMethod method;
    private DonationStatus status;
    private ZonedDateTime donationDateTime;
    private String donorName;
    private String donorPhone;

}
