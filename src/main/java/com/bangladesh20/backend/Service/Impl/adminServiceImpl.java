package com.bangladesh20.backend.Service.Impl;

import com.bangladesh20.backend.Common.Response.ApiResponse;
import com.bangladesh20.backend.Common.Response.PageMetaData;
import com.bangladesh20.backend.Dto.AdminDtos.userDetailsDto;
import com.bangladesh20.backend.Entity.Role;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Repository.RoleRepository;
import com.bangladesh20.backend.Repository.authRepository;
import com.bangladesh20.backend.Service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class adminServiceImpl implements AdminService {
    private final authRepository authRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Override
    public Long deleteUser(Long id) {
        if (!authRepository.existsById(id)) {
            throw new IllegalArgumentException("No Student Found With Id: " + id);
        }
        authRepository.deleteById(id);
        return id;
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

    private PageMetaData buildPageMetadata(Page<?> page) {
        int currentPage = page.getNumber();
        int totalPages = page.getTotalPages();
        boolean hasNext = page.hasNext();
        boolean hasPrev = page.hasPrevious();

        return PageMetaData.builder()
                .currentPage(currentPage)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(totalPages)
                .hasNext(hasNext)
                .hasPrevious(hasPrev)
                .nextPage(hasNext ? currentPage + 1 : null)   // null = no next
                .previousPage(hasPrev ? currentPage - 1 : null)   // null = no previous
                .build();
    }

    public ApiResponse<List<userDetailsDto>> getUsers(int page, int size, String search, String
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

        List<userDetailsDto> dtoList = userPage.getContent()
                .stream()
                .map(user -> {
                    userDetailsDto dto = modelMapper.map(user, userDetailsDto.class);
                    dto.setRoles(
                            user.getRoles().stream()
                                    .map(roles -> roles.getName())   // ✅ extract name string
                                    .collect(Collectors.toSet())
                    );
                    dto.setJoined(
                            user.getCreatedAt()
                    );
                    return dto;
                })
                .collect(Collectors.toList());
// Build pagination metadata with nextPage / previousPage
        PageMetaData pagination = buildPageMetadata(userPage);

        return ApiResponse.success("Users fetched successfully", dtoList, pagination);
    }


}

