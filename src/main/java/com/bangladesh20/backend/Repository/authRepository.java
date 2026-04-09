package com.bangladesh20.backend.Repository;

import com.bangladesh20.backend.Entity.Role;
import com.bangladesh20.backend.Entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface authRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String username);
    boolean existsByUsername(String username);

    @Override
    Page<Users> findAll(Pageable pageable);

    Page<Users> findByUsernameContainingIgnoreCase(String name,Pageable pageable);
    Page<Users>findByRolesAndUsernameContainingIgnoreCase(Role role, String name, Pageable pageable);
    Page<Users> findByRoles(Role roles, Pageable pageable);
}
