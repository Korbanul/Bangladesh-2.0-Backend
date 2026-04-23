package com.bangladesh20.backend.Service;
import com.bangladesh20.backend.Service.Impl.ImagelistDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AdminService {


    Map<String,Object> getUsers(int page, int size, String search, String sortBy, String sortDir, String role);


    Long deleteUser(Long id);

    Map UploadImage(MultipartFile file);

    List<ImagelistDto> getAllImage();

    ResponseEntity<Long> getTotalUserCount();

    ResponseEntity<Long> getTotalImageCount();

    ResponseEntity<BigDecimal> getTotalDonationAmount();
}
