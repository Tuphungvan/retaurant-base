package com.pvt.restaurant.service;

import com.pvt.restaurant.domain.enumration.Category;
import com.pvt.restaurant.service.dto.request.FoodRequest;
import com.pvt.restaurant.service.dto.response.FoodResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FoodService {
    Page<FoodResponse> getFoodForRestaurantNameCategory(Pageable pageable, UUID restaurantId, String name, Category category);

    Page<FoodResponse> getFoodPage(Pageable pageable);

    FoodResponse getFood(UUID id);

    FoodResponse createFood(UUID restaurantId, FoodRequest request);

    FoodResponse updateFood(UUID id, FoodRequest request);

    FoodResponse updateAvailableFood(UUID id, Boolean isAvailable);

    Boolean deleteFood(UUID id);
}
