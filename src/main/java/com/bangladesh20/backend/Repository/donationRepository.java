package com.bangladesh20.backend.Repository;

import com.bangladesh20.backend.Entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Valid;
import java.util.List;

public interface donationRepository extends JpaRepository<Donation,Long> {
    List<Donation> findAll();
    List<Donation> findByUserId(Long userId);
}
