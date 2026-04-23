package com.bangladesh20.backend.Repository;

import com.bangladesh20.backend.Entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

public interface donationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findAll();

    List<Donation> findByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d")
    BigDecimal getTotalAmount();
    //COALESCE(..., 0) returns 0 instead of null when table is empty.
}
