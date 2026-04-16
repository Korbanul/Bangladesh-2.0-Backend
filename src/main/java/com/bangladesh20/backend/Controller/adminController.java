package com.bangladesh20.backend.Controller;
import com.bangladesh20.backend.Dto.AdminDtos.PaymentMethodRequestDto;
import com.bangladesh20.backend.Dto.Donation.DonationResponseDto;
import com.bangladesh20.backend.Dto.Donation.PaymentMethodResponseDto;
import com.bangladesh20.backend.Service.AdminService;
import com.bangladesh20.backend.Service.donationService;
import com.bangladesh20.backend.Service.paymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class adminController {
   private final AdminService adminService;
   private final paymentMethodService paymentMethodService;
   private final donationService donationService;

    @DeleteMapping("/deleteuser/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<Long > deleteUser(@PathVariable Long id){
        adminService.deleteUser(id);
        return ResponseEntity.accepted().body(id);
    }

    @GetMapping("/users")
    @PreAuthorize(("hasAuthority('user:manage')"))
    public ResponseEntity<Map<String,Object>>getUsers(
            @RequestParam(defaultValue = "0" ) @Min(0) int page,
            @RequestParam(defaultValue = "10") @Max(100) int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asce") String sortDir,
            @RequestParam(required = false)String role
            )
    {
//        ApiResponse<List<userDetailsDto>> response= adminService.getUsers(page,size,search,sortBy,sortDir,role);

        return ResponseEntity.ok(adminService.getUsers(page,size,search,sortBy,sortDir,role));
    }

    @PutMapping("/add-payment-method")

    public ResponseEntity<PaymentMethodResponseDto> addPaymentMethod(@RequestBody @Valid PaymentMethodRequestDto paymentMethodRequestDto)
    {

        return ResponseEntity.ok(paymentMethodService.addPaymentMethod(paymentMethodRequestDto));
    }

    @GetMapping ("/donation-list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DonationResponseDto>>DonationList(){
        return ResponseEntity.ok(donationService.donationList());
    }



}
