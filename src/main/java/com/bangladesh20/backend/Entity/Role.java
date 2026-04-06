package com.bangladesh20.backend.Entity;

import lombok.*;
import org.hibernate.annotations.JoinFormula;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // "ROLE_ADMIN", "ROLE_DOCTOR", "ROLE_PATIENT"

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_authority",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")

    )
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();


    @Override
    public String getAuthority() {
        return name;
    }

    // Called by User.getAuthorities() — returns ROLE_ADMIN + all its permissions
    public Set<GrantedAuthority> getAllAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(this.authorities);
        grantedAuthorities.add(this);
        return grantedAuthorities;
    }
}
