package com.pvt.restaurant.service.impl;

import com.pvt.restaurant.domain.Food;
import com.pvt.restaurant.domain.enumration.Category;
import com.pvt.restaurant.repository.FoodRepository;
import com.pvt.restaurant.repository.RestaurantRepository;
import com.pvt.restaurant.service.FoodService;
import com.pvt.restaurant.service.dto.request.FoodRequest;
import com.pvt.restaurant.service.dto.response.FoodResponse;
import com.pvt.restaurant.service.mapper.FoodMapper;
import com.pvt.restaurant.web.response.BussinessException;
import com.pvt.restaurant.web.rest.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepo;
    private final FoodMapper foodMapper;
    private final RestaurantRepository restaurantRepo;

    @Override
    @Transactional(readOnly = true)
    public Page<FoodResponse> getFoodForRestaurantNameCategory(Pageable pageable, UUID restaurantId, String name, Category category) {
        Page<Food> foods = foodRepo.findFoodForRestaurantNameCategory(restaurantId, name, category, pageable);
        return foods.map(foodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FoodResponse> getFoodPage(Pageable pageable) {
        Page<Food> foods = foodRepo.findAllByDeletedAtIsNull(pageable);
        return foods.map(foodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public FoodResponse getFood(UUID id) {
        Food food = foodRepo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BussinessException(ErrorCode.food_active_not_found_by_id));
        return foodMapper.toDto(food);
    }

    @Override
    @Transactional
    public FoodResponse createFood(UUID restaurantId, FoodRequest request) {
        if (!restaurantRepo.existsByIdAndDeletedAtIsNull(restaurantId)) {
            throw new BussinessException(ErrorCode.restaurant_active_not_found_by_id);
        }
        if (foodRepo.existsByRestaurantIdAndNameAndDeletedAtIsNull(restaurantId, request.getName())) {
            throw new BussinessException(ErrorCode.food_active_exists);
        }
        Food food = foodMapper.toEntity(request);
        food.setRestaurantId(restaurantId);
        foodRepo.save(food);
        return foodMapper.toDto(food);
    }

    @Override
    @Transactional
    public FoodResponse updateFood(UUID id, FoodRequest request) {
        Food food = foodRepo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BussinessException(ErrorCode.food_active_not_found_by_id));
        food.setName(request.getName());
        food.setImage_url(request.getImage_url());
        food.setPrice(request.getPrice());
        food.setCategory(request.getCategory());
        foodRepo.save(food);
        return foodMapper.toDto(food);
    }

    @Override
    @Transactional
    public FoodResponse updateAvailableFood(UUID id, Boolean isAvailable) {
        Food food = foodRepo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BussinessException(ErrorCode.food_active_not_found_by_id));
        food.setIsAvailable(isAvailable);
        foodRepo.save(food);
        return foodMapper.toDto(food);
    }

    @Override
    @Transactional
    public Boolean deleteFood(UUID id) {
        Food food = foodRepo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BussinessException(ErrorCode.food_active_not_found_by_id));
        food.setDeletedAt(Instant.now());
        foodRepo.save(food);
        return true;
    }
}
