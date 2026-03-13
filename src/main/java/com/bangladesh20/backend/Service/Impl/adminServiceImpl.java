package com.bangladesh20.backend.Service.Impl;

import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Repository.authRepository;
import com.bangladesh20.backend.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class adminServiceImpl implements AdminService {
    private final authRepository authRepository;
    @Override
    public Long deleteUser(Long id) {
        if(!authRepository.existsById(id)){
            throw new IllegalArgumentException("No Student Found With Id: "+id);
        }
        authRepository.deleteById(id);
        return id;
    }
}
