package com.bangladesh20.backend.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "uploaded_images")
public class Images  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String publicId; //Cloudinary public ID (used to delete/transform)

    private String originalFileName;
    private String format;          // jpg, png, webp...
    private Long   sizeBytes;
    private int    width;
    private int    height;

    //When any image uploaded it will auto set the date.
    //Without this when we use builder the could we set with build that will set else columns will be null
    // We will get an error.  So use @CreationTimestamp Hibernate injects the timestamp before the SQL INSERT fires
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();


}
