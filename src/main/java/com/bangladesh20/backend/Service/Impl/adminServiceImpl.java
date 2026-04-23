package com.bangladesh20.backend.Service.Impl;

import com.bangladesh20.backend.Dto.AdminDtos.userDetailsDto;
import com.bangladesh20.backend.Entity.Images;
import com.bangladesh20.backend.Entity.Role;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Repository.ImagesRepository;
import com.bangladesh20.backend.Repository.RoleRepository;
import com.bangladesh20.backend.Repository.authRepository;
import com.bangladesh20.backend.Repository.donationRepository;
import com.bangladesh20.backend.Service.AdminService;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class adminServiceImpl implements AdminService {
    private final authRepository authRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final Cloudinary cloudinary;
    private final ImagesRepository imagesRepository;
    private final donationRepository donationRepository;

    @Override
    public Long deleteUser(Long id) {
        if (!authRepository.existsById(id)) {
            throw new IllegalArgumentException("No Student Found With Id: " + id);
        }
        authRepository.deleteById(id);
        return id;
    }



    @Override
    public Map UploadImage(MultipartFile file) {
        Map<String, Object> options = new HashMap();
        String publicId = null;  // track publicId for rollback
//        This Map is the options/config you send to Cloudinary telling it how to handle your upload.
//         Think of it as query parameters for the Cloudinary API.
        options.put("folder", "admin-uploads");
        options.put("resource_type", "image");
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), options);
                    publicId=(String) result.get("public_id");
            Images images=Images.builder()
                    .imageUrl((String) result.get("secure_url"))
                    .format((String) result.get("format"))
                    .publicId((String) result.get("public_id"))
                    .sizeBytes(((Number) result.get("bytes")).longValue())
                    .width((int)result.get("width"))
                    .height((int)result.get("height"))

                    .build();

            images.setOriginalFileName(file.getOriginalFilename());

            imagesRepository.save(images);
            return result;
        } catch (IOException e) {

            throw new RuntimeException("Image Upload Exception From Cloudinary" + e.getMessage());
        }catch (Exception e) {
            // This will run when image already uploaded to the cloudinary but due to any error details not saved to our DB
            // Then we need to delete the image form cloudinary. To avoid duplicate image in Cloudinary.
            if (publicId != null) {
                try {
                    cloudinary.uploader().destroy(publicId, new HashMap<>());
                    log.warn("Rolled back Cloudinary upload: {}", publicId);
                } catch (IOException rollbackEx) {
                    // If delete failed
                    log.error("Cloudinary rollback failed for publicId: {}. Manual cleanup needed.", publicId);
                }
            }
            throw new RuntimeException("Image save failed, upload rolled back: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ImagelistDto> getAllImage() {

        List<Images> images = imagesRepository.findAll();


        return images.stream().map((image)->(modelMapper.map(image, ImagelistDto.class))).collect(Collectors.toList()) ;
    }




    @Override
    public ResponseEntity<Long> getTotalUserCount() {

       Long totaluser=authRepository.count();
        return ResponseEntity.ok(totaluser);
    }

    @Override
    public ResponseEntity<Long> getTotalImageCount() {
        Long totalimage =  imagesRepository.count();
        return ResponseEntity.ok(totalimage);
    }


    @Override
    public ResponseEntity<BigDecimal> getTotalDonationAmount() {
        return ResponseEntity.ok(donationRepository.getTotalAmount());
    }





    private static final Set<String> ALLOWED_SORT_FIELDS = new HashSet<>(Arrays.asList("id", "username", "createdAt", "roles"));

    private Page<Users> resolveQuery(String search, Role roles, Pageable pageable) {
        boolean hasSearch = search != null && !search.trim().isEmpty();
        boolean hasRole = roles != null;

        if (hasSearch && hasRole) {
            return authRepository
                    .findByRolesAndUsernameContainingIgnoreCase(roles, search, pageable);
        } else if (hasSearch) {
            return authRepository
                    .findByUsernameContainingIgnoreCase(
                            search, pageable);
        } else if (hasRole) {
            return authRepository.findByRoles(roles, pageable);
        } else {
            return authRepository.findAll(pageable);
        }
    }

//    private PageMetaData buildPageMetadata(Page<?> page) {
//        int currentPage = page.getNumber();
//        int totalPages = page.getTotalPages();
//        boolean hasNext = page.hasNext();
//        boolean hasPrev = page.hasPrevious();
//
//        return PageMetaData.builder()
//                .currentPage(currentPage)
//                .pageSize(page.getSize())
//                .totalElements(page.getTotalElements())
//                .totalPages(totalPages)
//                .hasNext(hasNext)
//                .hasPrevious(hasPrev)
//                .nextPage(hasNext ? currentPage + 1 : null)   // null = no next
//                .previousPage(hasPrev ? currentPage - 1 : null)   // null = no previous
//                .build();
//    }

    public Map<String, Object> getUsers(int page, int size, String search, String
            sortBy, String sortDir, String roleName) {

        log.info("Fetching users — page={}, size={}, search={}, sortBy={}, sortDir={}, role={}",
                page, size, search, sortBy, sortDir, roleName);

        // Validate sort field (security — prevent arbitrary field injection)
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            sortBy = "createdAt";
        }
        Role role = null;
        if (roleName != null && !roleName.trim().isEmpty()) {
            role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        }


        // Build Sort
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Query
        Page<Users> userPage = resolveQuery(search, role, pageable);
//  Validate AFTER query — we need totalPages first  if user input page=100 then show error
        if (page > 0 && page >= userPage.getTotalPages()) {
            throw new RuntimeException("Page " + page + " does not exist. Total pages: " + userPage.getTotalPages());
        }
        List<userDetailsDto> dtoList = userPage.getContent()
                .stream()
                .map(user -> {
                    userDetailsDto dto = modelMapper.map(user, userDetailsDto.class);
                    dto.setRoles(
                            user.getRoles().stream()
                                    .map(roles -> roles.getName())   // extract name string
                                    .collect(Collectors.toSet())
                    );
                    dto.setJoined(
                            user.getCreatedAt()
                    );
                    return dto;
                })
                .collect(Collectors.toList());

// PageMetaData pagination = buildPageMetadata(userPage);
        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", userPage.getNumber());
        pagination.put("totalPages", userPage.getTotalPages());
        pagination.put("totalElements", userPage.getTotalElements());
        pagination.put("pageSize", userPage.getSize());
        pagination.put("hasNext", userPage.hasNext());
        pagination.put("hasPrevious", userPage.hasPrevious());
        pagination.put("nextPage", userPage.hasNext() ? userPage.getNumber() + 1 : null);
        pagination.put("previousPage", userPage.hasPrevious() ? userPage.getNumber() - 1 : null);


        Map<String, Object> response = new HashMap<>();
        response.put("data", dtoList);
        response.put("pagination", pagination);   // ← nested object

        return response;

    }


}

