package com.bangladesh20.backend.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="paymentmethod")
public class paymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private String logoUrl;

    private Boolean active =true;



}
