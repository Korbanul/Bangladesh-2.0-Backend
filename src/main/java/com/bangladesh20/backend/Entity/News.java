package com.bangladesh20.backend.Entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String publicId;

    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")// Because by default String= VARCHAR(255) 255 charsShort in Database.
    // We need long text, TEXT65,535 otherwise throw error.
    private String description;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
