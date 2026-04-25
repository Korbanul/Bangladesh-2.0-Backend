package com.bangladesh20.backend.Service;


import com.bangladesh20.backend.Dto.NewsDto.NewsResponseDto;

import java.util.List;

public interface NewsService {
    public List<NewsResponseDto> RecentThreeNews();

    Long totalNewsCount();
}
