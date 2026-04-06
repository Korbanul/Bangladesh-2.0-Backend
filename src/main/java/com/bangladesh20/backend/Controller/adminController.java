package com.bangladesh20.backend.Controller;

import com.bangladesh20.backend.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class adminController {
   private final AdminService adminService;
    @DeleteMapping("/deleteuser/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<Long > deleteUser(@PathVariable Long id){
        adminService.deleteUser(id);
        return ResponseEntity.accepted().body(id);
    }



}
