package com.bangladesh20.backend.Dto.NewsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDto {
    private Long id;
    private String title;
    private String description;
    private String imgUrl;
    private LocalDateTime createdAt;
}