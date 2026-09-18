package com.pvt.restaurant.service;

import com.pvt.restaurant.service.dto.request.RestaurantRequest;
import com.pvt.restaurant.service.dto.response.RestaurantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RestaurantService {
    RestaurantResponse createRestaurant(RestaurantRequest request);

    RestaurantResponse updateRestaurant(UUID id, RestaurantRequest request);

    RestaurantResponse closeOpenRestaurant(UUID id, Boolean isOpen);

    Boolean deleteRestaurant(UUID id);

    Page<RestaurantResponse> getRestaurantPage(Pageable pageable);

    RestaurantResponse findRestaurant(UUID id);
}
