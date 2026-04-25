package com.bangladesh20.backend.Service;

import com.bangladesh20.backend.Dto.Auth.ProfileResponseDto;
import com.bangladesh20.backend.Dto.NewsDto.NewsResponseDto;
import com.bangladesh20.backend.Dto.Userservice.UserUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface userService {
    public ProfileResponseDto getUser(Long id);

    Object updateUser(Long id, UserUpdateDto userUpdateDto);

    ResponseEntity<Long> getTotalImageCount();

    ResponseEntity<BigDecimal> getTotalDonationAmount();

    List<NewsResponseDto> GetAllNews();


    NewsResponseDto GetNews(Long id);
}
