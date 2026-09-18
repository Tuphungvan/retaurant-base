package com.pvt.restaurant.repository;

import com.pvt.restaurant.domain.Food;
import com.pvt.restaurant.domain.enumration.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {
    @Query(
            """
                    select f from Food f where f.restaurantId = :restaurantId 
                    and f.deletedAt is null 
                    and (:name is null or f.name like lower(concat('%',:name,'%'))) 
                    and (:category is null or f.category = :category)
                    """
    )
    Page<Food> findFoodForRestaurantNameCategory(
            @Param("restaurantId") UUID restaurantId,
            @Param("name") String name,
            @Param("category") Category category,
            Pageable pageable
            );

    Page<Food> findAllByDeletedAtIsNull(Pageable pageable);

    boolean existsByRestaurantIdAndNameAndDeletedAtIsNull(UUID restaurantId, String name);

    Optional<Food> findByIdAndDeletedAtIsNull(UUID id);
}