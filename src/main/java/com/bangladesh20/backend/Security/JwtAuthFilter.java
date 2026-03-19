package com.bangladesh20.backend.Security;

import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Repository.authRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenGenerate jwtTokenGenerate;
    private final authRepository authRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("Incoming request{}", request.getRequestURI());
            String token = null;

            // ✅ Get token from Header
//            final String requestTokenHeader = request.getHeader("Authorization");
//
//            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            String Token = requestTokenHeader.split("Bearer ")[1];

            // ✅ Get token from cookies when we use middleware in frontend
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                    }
                }
            }
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }


            String username = jwtTokenGenerate.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final Users users = authRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
                UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(users, null, users.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token1);

            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("JWT Filter error: {}", ex.getMessage());
            filterChain.doFilter(request, response);
        }

    }
}
