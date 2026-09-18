package com.pvt.restaurant.repository;

import com.pvt.restaurant.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    Page<Restaurant> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<Restaurant> findByIdAndDeletedAtIsNull(UUID id);
    boolean existsByNameAndPhoneAndAddressAndDeletedAtIsNull(String name, String phone, String address);
    boolean existsByIdAndDeletedAtIsNull(UUID id);
}
