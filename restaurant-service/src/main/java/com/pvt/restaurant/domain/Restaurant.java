package com.pvt.restaurant.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 13, nullable = false)
    private String phone;

    @Column(nullable = false, columnDefinition = "TEXT", length = 500)
    private String address;

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen = true;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
