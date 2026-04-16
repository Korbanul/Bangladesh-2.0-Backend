package com.bangladesh20.backend.Repository;

import com.bangladesh20.backend.Entity.paymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface paymentMethodRepository extends JpaRepository<paymentMethod,Long> {
    List<paymentMethod> findAll();
    List<paymentMethod> findByActiveTrue();

    Optional<paymentMethod> findByIdAndActiveTrue(Long id);
}
