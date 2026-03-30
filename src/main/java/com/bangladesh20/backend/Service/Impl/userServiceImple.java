package com.bangladesh20.backend.Service.Impl;


import com.bangladesh20.backend.Dto.Auth.ProfileResponseDto;
import com.bangladesh20.backend.Dto.Userservice.UserUpdateDto;
import com.bangladesh20.backend.Entity.Type.Gender;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Repository.authRepository;
import com.bangladesh20.backend.Service.userService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class userServiceImple implements userService {
    private final authRepository authRepository;
    private final ModelMapper modelmapper;

    @Override
    public ProfileResponseDto getUser(Long id) {
        Users user = authRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));


        return new ProfileResponseDto(user.getUsername(), user.getEmail(), user.getRoles().stream()
                .map(role -> role.name())
                .collect(java.util.stream.Collectors.joining(", ")), user.getProfession(), user.getGender(), user.getDob(), user.getCreatedAt());
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
}

