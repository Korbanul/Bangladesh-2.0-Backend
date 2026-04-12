package com.bangladesh20.backend.Service;
import java.util.Map;

public interface AdminService {


    Map<String,Object> getUsers(int page, int size, String search, String sortBy, String sortDir, String role);


    Long deleteUser(Long id);
}
