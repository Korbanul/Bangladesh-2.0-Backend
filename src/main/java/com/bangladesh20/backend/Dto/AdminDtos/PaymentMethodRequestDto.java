package com.bangladesh20.backend.Dto.AdminDtos;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PaymentMethodRequestDto {

    @NotNull
    private String name;
    @NotNull
    private String logoUrl;
}
