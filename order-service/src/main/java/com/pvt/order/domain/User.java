package com.pvt.order.domain;

import com.pvt.order.domain.enumration.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    UUID id;

    @Column(nullable = false, unique = true)
    String login;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    UserStatus status = UserStatus.ACTIVE;

    @Column(nullable = false)
    @Builder.Default
    Boolean loginVerified = false;

    @Column(unique = true)
    String googleId;

    String passwordHash;
    Instant passwordChangedAt;

    @CreationTimestamp
    @Column(nullable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    Instant updatedAt;
}
