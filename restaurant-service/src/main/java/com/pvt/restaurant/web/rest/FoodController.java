package com.pvt.restaurant.web.rest;

import com.pvt.restaurant.domain.enumration.Category;
import com.pvt.restaurant.service.FoodService;
import com.pvt.restaurant.service.dto.request.FoodRequest;
import com.pvt.restaurant.service.dto.response.FoodResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/restaurants/{restaurantId}/foods")
    public ResponseEntity<Page<FoodResponse>> getFoodsByRestaurant(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) Category category,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(foodService.getFoodForRestaurantNameCategory(pageable, restaurantId, name, category));
    }

    @PostMapping("/restaurants/{restaurantId}/foods")
    public ResponseEntity<FoodResponse> createFood(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody FoodRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(restaurantId, request));
    }

    @GetMapping("/foods")
    public ResponseEntity<Page<FoodResponse>> getFoods(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(foodService.getFoodPage(pageable));
    }

    @GetMapping("/foods/{id}")
    public ResponseEntity<FoodResponse> getFood(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(foodService.getFood(id));
    }

    @PutMapping("/foods/{id}")
    public ResponseEntity<FoodResponse> updateFood(
            @PathVariable("id") UUID id,
            @RequestBody FoodRequest request
    ) {
        return ResponseEntity.ok(foodService.updateFood(id, request));
    }

    @PatchMapping("/foods/{id}/availability")
    public ResponseEntity<FoodResponse> updateAvailability(
            @PathVariable("id") UUID id,
            @RequestParam("isAvailable") Boolean isAvailable
    ) {
        return ResponseEntity.ok(foodService.updateAvailableFood(id, isAvailable));
    }

    @DeleteMapping("/foods/{id}")
    public ResponseEntity<Boolean> deleteFood(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(foodService.deleteFood(id));
    }
}
