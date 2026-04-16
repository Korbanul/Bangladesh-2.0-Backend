package com.bangladesh20.backend.Dto.Donation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationRequestDto {
    @NotNull
    @Min(value = 20,message = "Minimum 20 taka")
    @Max(value = 100000,message = "Maximum 100,000 taka")
    private BigDecimal amount;
    @NotNull
    private Long paymentMethodId;
    private String name;
    private String phone;
}
