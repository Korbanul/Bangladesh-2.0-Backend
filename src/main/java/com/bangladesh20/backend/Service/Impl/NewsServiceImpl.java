package com.bangladesh20.backend.Service.Impl;

import com.bangladesh20.backend.Dto.NewsDto.NewsResponseDto;
import com.bangladesh20.backend.Entity.News;
import com.bangladesh20.backend.Repository.NewsRepository;
import com.bangladesh20.backend.Service.NewsService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final ModelMapper modalmapper;
    @Override
    public List<NewsResponseDto> RecentThreeNews() {
        List<News> newsList= newsRepository.findTop3ByOrderByCreatedAtDesc();

        return newsList.stream().map((news)->(modalmapper.map(news,NewsResponseDto.class))).collect(Collectors.toList());
    }

    @Override
    public Long totalNewsCount() {
        return newsRepository.count();
    }
}
