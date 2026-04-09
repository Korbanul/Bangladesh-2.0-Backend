package com.bangladesh20.backend.Seeder;

import com.bangladesh20.backend.Entity.Authority;
import com.bangladesh20.backend.Entity.Role;
import com.bangladesh20.backend.Entity.Users;
import com.bangladesh20.backend.Repository.AuthorityRepository;
import com.bangladesh20.backend.Repository.RoleRepository;
import com.bangladesh20.backend.Repository.authRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final authRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {

        if (roleRepository.existsByName("ROLE_ADMIN") && userRepository.existsByUsername("admin")) {
            log.info("DataSeeder: already seeded, skipping.");
            return;
        }

        log.info("DataSeeder: seeding started...");

        // ── 1. Create default authorities
        Authority profileRead  = createAuthority("profile:read");
        Authority profileWrite = createAuthority("profile:write");
        Authority newsRead     = createAuthority("news:read");
        Authority newsWrite    = createAuthority("news:write");
        Authority addImage     =createAuthority("image:add");
        Authority viewImage    =createAuthority("image:view");
        Authority donate    =createAuthority("donate:create");
        Authority manageDonate    =createAuthority("donate:manage");
        Authority manageUser    =createAuthority("user:manage");
        Authority deleteUser    =createAuthority("user:delete");


        // ── 2. Create ROLE_USER with default authorities
        Set<Authority> userAuthorities = new HashSet<>(Arrays.asList(
                profileRead,profileWrite,
                newsRead, newsWrite,
                viewImage,donate
        ));
        syncRole("ROLE_USER", userAuthorities);

        // ── 3. Create ROLE_ADMIN
        Set<Authority> adminAuthorities = new HashSet<>(Arrays.asList(
                profileRead, profileWrite,
                newsRead, newsWrite,
                deleteUser,manageDonate,
                addImage,manageUser
        ));
        Role roleAdmin = syncRole("ROLE_ADMIN", adminAuthorities);

        // ── 4. Create first ADMIN user
        if (!userRepository.existsByUsername("admin")) {

            Set<Role> adminRoles = new HashSet<>(Arrays.asList(roleAdmin));

            Users admin = Users.builder()
                    .username("admin")
                    .email("admin@bangladesh20.com")
                    .password(passwordEncoder.encode("admin123"))
                    .profession("System Administrator")
                    .gender(com.bangladesh20.backend.Entity.Type.Gender.MALE)
                    .dob(LocalDate.of(1990, 1, 1))
                    .roles(adminRoles)
                    .build();

            userRepository.save(admin);
            log.info("DataSeeder: admin created — username: admin, password: admin123");
        }

        log.info("DataSeeder: seeding complete.");
    }

    private Authority createAuthority(String name) {
        return authorityRepository.findByName(name)
                .orElseGet(() -> authorityRepository.save(
                        Authority.builder().name(name).build()
                ));
    }

    // ✅ Always updates authorities regardless
    private Role syncRole(String name, Set<Authority> authorities) {
        Role role = roleRepository.findByName(name)
                .orElse(Role.builder().name(name).build());
        role.setAuthorities(authorities); // always overwrite
        return roleRepository.save(role);
    }
}