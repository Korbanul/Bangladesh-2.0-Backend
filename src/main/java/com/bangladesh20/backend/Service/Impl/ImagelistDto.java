package com.bangladesh20.backend.Service.Impl;

import lombok.Data;

@Data
public class ImagelistDto {
    private String imageUrl;
    private String publicId;
//    DTO Fields must Match Entity Fields
//    ModelMapper maps by matching field names exactly
}
