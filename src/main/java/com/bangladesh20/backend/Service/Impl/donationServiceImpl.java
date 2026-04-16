package com.bangladesh20.backend.Service.Impl;

import com.bangladesh20.backend.Dto.Donation.DonationRequestDto;
import com.bangladesh20.backend.Dto.Donation.DonationResponseDto;
import com.bangladesh20.backend.Entity.Donation;
import com.bangladesh20.backend.Entity.Type.DonationStatus;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Entity.paymentMethod;
import com.bangladesh20.backend.Repository.authRepository;
import com.bangladesh20.backend.Repository.donationRepository;
import com.bangladesh20.backend.Repository.paymentMethodRepository;
import com.bangladesh20.backend.Service.donationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class donationServiceImpl implements donationService {

    private final paymentMethodRepository paymentMethodRepository;
    private final authRepository authRepository;
    private final donationRepository donationRepository;
    private final ModelMapper modelmapper;

    @Override
    public DonationResponseDto guestDonate(DonationRequestDto donationRequestDto) {

        Donation donation = new Donation();
        donation.setGuestName(donationRequestDto.getName());
        donation.setGuestPhone(donationRequestDto.getPhone());
        donation.setAmount(donationRequestDto.getAmount());
        donation.setTransactionId(UUID.randomUUID().toString());
        donation.setStatus(DonationStatus.COMPLETED);

        paymentMethod method = paymentMethodRepository.
                findByIdAndActiveTrue(donationRequestDto.getPaymentMethodId())
                .orElseThrow(() -> new IllegalArgumentException("Payment Method Not Found"));

        donation.setMethod(method);
        donation.setDateAndTime(ZonedDateTime.now());

        donationRepository.save(donation);

        DonationResponseDto donationResponseDto = DonationResponseDto.builder()
                .donorName(donation.getGuestName())
                .transactionId(donation.getTransactionId())
                .amount(donation.getAmount())
                .method(donation.getMethod())
                .status(donation.getStatus())
                .donationDateTime(donation.getDateAndTime())
                .build();
        return donationResponseDto;
    }

    @Override
    public List<DonationResponseDto> donationList() {
        List<Donation> donations = donationRepository.findAll();


//        return (donations.stream().map((item) ->
//                (modelmapper.map(item, DonationResponseDto.class))))
//                .collect(Collectors.toList());
        return (donations.stream().map((item) ->
                DonationResponseDto.builder()
                        .transactionId(item.getTransactionId())
                        .amount(item.getAmount())
                        .id(item.getId())
                        .method(item.getMethod())
                        .status(item.getStatus())
                        .donorName(item.getUser() == null ? item.getGuestName() : item.getUser().getUsername())
                        .donationDateTime(item.getDateAndTime())
                        .donorPhone(item.getUser() == null ? item.getGuestPhone() : item.getUserPhone())
                        .build()
        ))
                .collect(Collectors.toList());
    }

    @Override
    public DonationResponseDto userDonate(DonationRequestDto donationRequestDto) {
        Donation donation = new Donation();
        Users user = authRepository.findByUsername(donationRequestDto.getName()).orElseThrow(() ->
         new UsernameNotFoundException("User not Found"));
        donation.setUser(user);
        donation.setUserPhone(donationRequestDto.getPhone());
        donation.setAmount(donationRequestDto.getAmount());
        donation.setTransactionId(UUID.randomUUID().toString());
        donation.setStatus(DonationStatus.COMPLETED);

        paymentMethod method = paymentMethodRepository.
                findByIdAndActiveTrue(donationRequestDto.getPaymentMethodId())
                .orElseThrow(() -> new IllegalArgumentException("Payment Method Not Found"));

        donation.setMethod(method);
        donation.setDateAndTime(ZonedDateTime.now());

        donationRepository.save(donation);

        DonationResponseDto donationResponseDto = DonationResponseDto.builder()
                .donorName(user.getUsername())
                .transactionId(donation.getTransactionId())
                .amount(donation.getAmount())
                .method(donation.getMethod())
                .status(donation.getStatus())
                .donorPhone(donation.getUserPhone())
                .donationDateTime(donation.getDateAndTime())
                .build();
        return donationResponseDto;
    }

    @Override
    public List<DonationResponseDto> userDonationList() {

        //Here i need current userId to find all his donation History.
        //So we can find it from SecurityContextHolder
        // Because we earlier save the Users object in SecurityContextHolder token when user login jwtAuthFilter

        Users user = (Users) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = user.getId();
        List<Donation> donations = donationRepository.findByUserId(userId);
        return (donations.stream().map((item) ->
                DonationResponseDto.builder()
                        .transactionId(item.getTransactionId())
                        .amount(item.getAmount())
                        .method(item.getMethod())
                        .status(item.getStatus())
                        .donorName(item.getUser().getUsername())
                        .donationDateTime(item.getDateAndTime())
                        .donorPhone(item.getUserPhone())
                        .build()
        ))
                .collect(Collectors.toList());
    }
}
