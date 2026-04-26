package com.bangladesh20.backend.Controller;

import com.bangladesh20.backend.Dto.AdminDtos.PaymentMethodRequestDto;
import com.bangladesh20.backend.Dto.Donation.DonationResponseDto;
import com.bangladesh20.backend.Dto.Donation.PaymentMethodResponseDto;
import com.bangladesh20.backend.Dto.NewsDto.NewsResponseDto;
import com.bangladesh20.backend.Service.AdminService;
import com.bangladesh20.backend.Service.Impl.ImagelistDto;
import com.bangladesh20.backend.Service.donationService;
import com.bangladesh20.backend.Service.paymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated //for  @NotBlank @Size(min = 3, max = 100)
public class adminController {
    private final AdminService adminService;
    private final paymentMethodService paymentMethodService;
    private final donationService donationService;

    @DeleteMapping("/deleteuser/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.accepted().body(id);
    }

    @GetMapping("/users")
    @PreAuthorize(("hasAuthority('user:manage')"))
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Max(100) int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asce") String sortDir,
            @RequestParam(required = false) String role
    ) {
//        ApiResponse<List<userDetailsDto>> response= adminService.getUsers(page,size,search,sortBy,sortDir,role);

        return ResponseEntity.ok(adminService.getUsers(page, size, search, sortBy, sortDir, role));
    }

    @PutMapping("/add-payment-method")

    public ResponseEntity<PaymentMethodResponseDto> addPaymentMethod(@RequestBody @Valid PaymentMethodRequestDto paymentMethodRequestDto) {

        return ResponseEntity.ok(paymentMethodService.addPaymentMethod(paymentMethodRequestDto));
    }

    @GetMapping("/donation-list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<DonationResponseDto>> DonationList() {
        return ResponseEntity.ok(donationService.donationList());
    }

    private static final List<String> ALLOWED_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/jpg", "/image/svg"
            //Here image/type this format use because getContentType() return MIME Type
//            file.getContentType()     // → "image/jpeg"   ← MIME type
//            file.getOriginalFilename() // → "photo.jpg"    ← filename with extension
    );

    @PostMapping("/upload-image")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map> UploadImage(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            Map error = new HashMap();
            error.put("error", "File is Empty");
            return ResponseEntity.badRequest().body(error);
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
//           I should use a library like Apache Tika which reads the actual file bytes to detect the real type —
//            because even MIME type headers can be manually forged.
            Map error = new HashMap();
            error.put("error", "Only JPG, SVG, JPEG and PNG are allowed.");
            return ResponseEntity.badRequest().body(error);
        }
//      Why RequestParam?  Without it, Spring doesn't know where to bind the file from the multipart request.
//        This will cause the parameter to always be null
        Map data = adminService.UploadImage(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/all-images")
    public List<ImagelistDto> getAllImage() {
        return adminService.getAllImage();
    }

    @GetMapping("/total-users")
    public ResponseEntity<Long> totalUser() {
        return adminService.getTotalUserCount();
    }


    @GetMapping("/total-image")

    public ResponseEntity<Long> totalImage() {
        return adminService.getTotalImageCount();
    }

    @GetMapping("/total-donation")
    public ResponseEntity<BigDecimal> totalDonation() {
        return adminService.getTotalDonationAmount();
    }


    @PostMapping("/add-news")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map> addNews(
            @RequestParam("title") @NotBlank @Size(min = 3, max = 100) String title,
            @RequestParam("description") @NotBlank @Size(min = 10) String description,
            @RequestParam("image") MultipartFile image) {


        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image is required.");
        }

        // MIME type check
        if (!ALLOWED_TYPES.contains(image.getContentType())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only JPEG, JPG, PNG, SVG allowed.");
        }

        adminService.createNews(title, description, image);

        Map<String, String> response = new HashMap<>();
        response.put("message", "News created successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all-news")
    public ResponseEntity<List<NewsResponseDto>> getAllNews(){

        return ResponseEntity.ok(adminService.GetAllNews());
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<NewsResponseDto> getNews(@PathVariable Long id){

        return ResponseEntity.ok(adminService.GetNews(id));
    }
    @DeleteMapping("/delete-news/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id){

        adminService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/payment-method/change-status/{id}")
    public ResponseEntity<Void>ChangeStatus(@PathVariable Long id) {
        adminService.chnageMethodStatus(id);
        return ResponseEntity.noContent().build();

    }



}
