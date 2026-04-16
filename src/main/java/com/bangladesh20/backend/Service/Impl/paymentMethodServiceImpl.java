package com.bangladesh20.backend.Service.Impl;

import com.bangladesh20.backend.Dto.AdminDtos.PaymentMethodRequestDto;
import com.bangladesh20.backend.Dto.Donation.PaymentMethodResponseDto;
import com.bangladesh20.backend.Entity.paymentMethod;
import com.bangladesh20.backend.Repository.paymentMethodRepository;
import com.bangladesh20.backend.Service.paymentMethodService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class paymentMethodServiceImpl implements paymentMethodService {
    private final paymentMethodRepository paymentMethodRepository;
    private final ModelMapper modelmapper;

    @Override
    public List<PaymentMethodResponseDto> getAllPaymentMethod() {


        return paymentMethodRepository.findAll()
                .stream()
                .map(item -> modelmapper.map(item, PaymentMethodResponseDto.class))
                //.toList();//Not available in Java 1.8
                .collect(Collectors.toList());
    }

    @Override
    public PaymentMethodResponseDto addPaymentMethod(PaymentMethodRequestDto paymentMethodRequestDto) {
        paymentMethod paymentMethod=new paymentMethod();
        paymentMethod.setName(paymentMethodRequestDto.getName());
        paymentMethod.setLogoUrl(paymentMethodRequestDto.getLogoUrl());
        paymentMethod.setActive(true);

        paymentMethod=paymentMethodRepository.save(paymentMethod);

        return (modelmapper.map(paymentMethod, PaymentMethodResponseDto.class));

    }

    @Override
    public List<PaymentMethodResponseDto> getActivePaymentMethod() {

        return paymentMethodRepository.findByActiveTrue()
                .stream().
                map((item)->PaymentMethodResponseDto.builder()
                        .id(item.getId()).name(item.getName()).logoUrl(item.getLogoUrl())
                        .build())
                .collect(Collectors.toList());

    }


}
