package com.bangladesh20.backend.Repository;

import com.bangladesh20.backend.Entity.Images;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images,Long> {
    @Override
     List<Images> findAll();
}
