package com.pvt.restaurant.web.rest;

import com.pvt.restaurant.service.RestaurantService;
import com.pvt.restaurant.service.dto.request.RestaurantRequest;
import com.pvt.restaurant.service.dto.response.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<Page<RestaurantResponse>> getRestaurants(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(restaurantService.getRestaurantPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurant(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(restaurantService.findRestaurant(id));
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody RestaurantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(
            @PathVariable("id") UUID id,
            @RequestBody RestaurantRequest request
    ) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RestaurantResponse> updateStatus(
            @PathVariable("id") UUID id,
            @RequestParam("isOpen") Boolean isOpen
    ) {
        return ResponseEntity.ok(restaurantService.closeOpenRestaurant(id, isOpen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRestaurant(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(restaurantService.deleteRestaurant(id));
    }
}
