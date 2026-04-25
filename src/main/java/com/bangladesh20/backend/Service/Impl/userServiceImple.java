package com.bangladesh20.backend.Service.Impl;


import com.bangladesh20.backend.Dto.Auth.ProfileResponseDto;
import com.bangladesh20.backend.Dto.NewsDto.NewsResponseDto;
import com.bangladesh20.backend.Dto.Userservice.UserUpdateDto;
import com.bangladesh20.backend.Entity.News;
import com.bangladesh20.backend.Entity.Role;
import com.bangladesh20.backend.Entity.Type.Gender;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Repository.ImagesRepository;
import com.bangladesh20.backend.Repository.NewsRepository;
import com.bangladesh20.backend.Repository.authRepository;
import com.bangladesh20.backend.Repository.donationRepository;
import com.bangladesh20.backend.Service.userService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class userServiceImple implements userService {
    private final authRepository authRepository;
    private final ModelMapper modelmapper;
    private final ImagesRepository imagesRepository;
    private final NewsRepository newsRepository;
    private final donationRepository donationRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProfileResponseDto getUser(Long id) {
        Users user = authRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)          // ← getName(), not name()
                .collect(Collectors.toSet());

        return new ProfileResponseDto(user.getUsername(), user.getEmail(), roles, user.getProfession(), user.getGender(), user.getDob(), user.getCreatedAt());
    }

    @Override
    public Object updateUser(Long id, UserUpdateDto userUpdateDto) {
        Users user = authRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));

        if(userUpdateDto.getUsername() !=null){
            user.setUsername(userUpdateDto.getUsername());
        }
        if(userUpdateDto.getProfession() !=null){
            user.setProfession(userUpdateDto.getProfession());
        }
        if(userUpdateDto.getGender() !=null){
            user.setGender(Gender.valueOf(userUpdateDto.getGender().toUpperCase()));
        }
        if(userUpdateDto.getDob() !=null){
            user.setDob(userUpdateDto.getDob());
        }


//        For Autmatic null check and update use below mapper. Internally same code just it make easy and create a mapping.

//        studentMapper.updateStudentFromDto(userUpdateDto, user); // null fields auto-skipped
//        @Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//        public interface StudentMapper {
//            void updateStudentFromDto(StudentPatchDto dto, @MappingTarget Student student);
//        }
        authRepository.save(user);
        return ResponseEntity.ok("Profile Updated");
    }

    @Override
    public ResponseEntity<Long> getTotalImageCount() {
      Long totalimage =  imagesRepository.count();
        return ResponseEntity.ok(totalimage);
    }

    @Override
    public ResponseEntity<BigDecimal> getTotalDonationAmount() {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(donationRepository.getTotalAmount(user.getId()));
    }

    @Override
    public List<NewsResponseDto> GetAllNews() {
        List<News> newsList =newsRepository.findAllByOrderByCreatedAtDesc();
        return newsList.stream().map((news)->(modelMapper.map(news, NewsResponseDto.class))).collect(Collectors.toList());
    }
}

