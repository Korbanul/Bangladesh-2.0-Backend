package com.bangladesh20.backend.Dto.Donation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PaymentMethodResponseDto {

    private Long id;
    private Boolean active;
    private String name;
    private String logoUrl;

}
