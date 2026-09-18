package com.pvt.restaurant.domain;

import com.pvt.restaurant.domain.enumration.Category;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "foods")
public class Food {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false, columnDefinition = "TEXT", length = 500)
    private String image_url;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
