package com.bangladesh20.backend.Repository;

import com.bangladesh20.backend.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface authRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String username);
}
