package com.bangladesh20.backend.Entity;

import com.bangladesh20.backend.Entity.Type.Gender;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String profession;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false, length = 100)
    private String password;

    @CreationTimestamp
    @Column(nullable = true)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER) //Because of EAGER, roles are loaded immediately with the user — no lazy loading needed. That's why getAuthorities() works without a separate fetch call (important for Spring Security context).
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    //@ManyToMany is a JPA relationship mapping where two entities relate
    // to each other through a separate join/junction table — not a column in either
    // entity's table.
    //User ↔ Role → a user can have many roles, a role can belong to many users.
    @Builder.Default
    private Set<Role> roles = new HashSet<>(); // New user_roles table will be created

    // Returns ROLE_USER + patient:read + appointment:write etc.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getAllAuthorities().stream())
                .collect(Collectors.toSet());
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
