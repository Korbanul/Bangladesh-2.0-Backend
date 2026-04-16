package com.bangladesh20.backend.Controller;

import com.bangladesh20.backend.Dto.Donation.DonationRequestDto;
import com.bangladesh20.backend.Dto.Donation.DonationResponseDto;
import com.bangladesh20.backend.Dto.Donation.PaymentMethodResponseDto;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Service.donationService;
import com.bangladesh20.backend.Service.paymentMethodService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/donate")
@AllArgsConstructor
public class donationController {
    private final donationService donationService;
    private final paymentMethodService paymentMethodService;
    @PostMapping("/guest")
    public ResponseEntity<DonationResponseDto> guestDonate( @RequestBody @Valid DonationRequestDto donationRequestDto){
        return ResponseEntity.ok(donationService.guestDonate(donationRequestDto));
    }
    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<DonationResponseDto> userDonate(@RequestBody @Valid DonationRequestDto donationRequestDto){
        return ResponseEntity.ok(donationService.userDonate(donationRequestDto));
    }

    @GetMapping("/payment-method")
    public ResponseEntity<List<PaymentMethodResponseDto>> getAllPaymentMethod(){
       return ResponseEntity.ok(paymentMethodService.getAllPaymentMethod());
    }
    @GetMapping("/active-payment-method")
    public ResponseEntity<List<PaymentMethodResponseDto>> getOnlyActivePaymentMethod(){
       return ResponseEntity.ok(paymentMethodService.getActivePaymentMethod());
    }
}
