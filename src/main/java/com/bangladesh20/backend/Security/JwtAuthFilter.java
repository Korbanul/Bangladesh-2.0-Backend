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

//            // ✅ Strategy 1: Read from Authorization header (Postman / mobile)
//            final String requestTokenHeader = request.getHeader("Authorization");
//            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//                token = requestTokenHeader.split("Bearer ")[1];
//            }

            // Strategy 2: Read from Cookie (Next.js rewrites / browser)
            if (token == null && request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            //No token found — skip auth, let Security decide (401/403)
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // Validate and set authentication
            String username = jwtTokenGenerate.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Users users = authRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(users, null, users.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            log.error("JWT Filter error: {}", ex.getMessage());
            filterChain.doFilter(request, response);
        }

    }
}
