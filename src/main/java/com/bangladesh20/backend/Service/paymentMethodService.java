package com.bangladesh20.backend.Service;

import com.bangladesh20.backend.Dto.AdminDtos.PaymentMethodRequestDto;
import com.bangladesh20.backend.Dto.Donation.PaymentMethodResponseDto;
import com.bangladesh20.backend.Entity.paymentMethod;

import javax.validation.Valid;
import java.util.List;

public interface paymentMethodService {
    List<PaymentMethodResponseDto> getAllPaymentMethod();

    PaymentMethodResponseDto addPaymentMethod(@Valid PaymentMethodRequestDto paymentMethodRequestDto);

    List<PaymentMethodResponseDto> getActivePaymentMethod();
}
