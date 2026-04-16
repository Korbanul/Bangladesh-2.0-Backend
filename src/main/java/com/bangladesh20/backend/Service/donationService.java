package com.bangladesh20.backend.Service;

import com.bangladesh20.backend.Dto.Donation.DonationRequestDto;
import com.bangladesh20.backend.Dto.Donation.DonationResponseDto;
import org.springframework.security.core.Authentication;

import javax.validation.Valid;
import java.util.List;

public interface donationService {
    DonationResponseDto guestDonate(@Valid DonationRequestDto donationRequestDto);

    List<DonationResponseDto> donationList();

    DonationResponseDto userDonate(@Valid DonationRequestDto donationRequestDto);

    List<DonationResponseDto> userDonationList();
}
