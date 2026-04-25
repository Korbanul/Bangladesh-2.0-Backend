package com.bangladesh20.backend.Controller;

import com.bangladesh20.backend.Dto.NewsDto.NewsResponseDto;
import com.bangladesh20.backend.Service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class homeController {
    private final NewsService newsService;

    @GetMapping("/home/recent-news")
    public ResponseEntity<List<NewsResponseDto>>getRecentThreeNews()
    {
        return ResponseEntity.ok(newsService.RecentThreeNews());
    }
    @GetMapping("/home/total-news")
    public ResponseEntity<Long>getNewsCount()
    {
        return ResponseEntity.ok(newsService.totalNewsCount());
    }
}
