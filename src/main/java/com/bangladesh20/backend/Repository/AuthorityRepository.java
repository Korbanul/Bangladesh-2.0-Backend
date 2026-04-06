package com.bangladesh20.backend.Repository;

import com.bangladesh20.backend.Entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    Optional<Authority> findByName(String name);
    boolean existsByName(String name);
}
