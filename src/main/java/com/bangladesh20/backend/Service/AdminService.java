package com.bangladesh20.backend.Service;

import com.bangladesh20.backend.Common.Response.ApiResponse;
import com.bangladesh20.backend.Dto.AdminDtos.userDetailsDto;
import com.bangladesh20.backend.Entity.Role;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

public interface AdminService {


    ApiResponse<List<userDetailsDto>> getUsers(int page, int size, String search, String sortBy, String sortDir, String role);


    Long deleteUser(Long id);
}
