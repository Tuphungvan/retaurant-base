package com.pvt.restaurant.service.mapper;

import com.pvt.restaurant.domain.Restaurant;
import com.pvt.restaurant.service.dto.request.RestaurantRequest;
import com.pvt.restaurant.service.dto.response.RestaurantResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {
    RestaurantResponse toDto(Restaurant restaurant);
    Restaurant toEntity(RestaurantRequest request);
}
