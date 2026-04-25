package com.bangladesh20.backend.Repository;

import com.bangladesh20.backend.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Long> {
    List<News> findAllByOrderByCreatedAtDesc();

    List<News> findTop3ByOrderByCreatedAtDesc();
}
