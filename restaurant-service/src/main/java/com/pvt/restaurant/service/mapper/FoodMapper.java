package com.pvt.restaurant.service.mapper;

import com.pvt.restaurant.domain.Food;
import com.pvt.restaurant.service.dto.request.FoodRequest;
import com.pvt.restaurant.service.dto.response.FoodResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    FoodResponse toDto(Food food);
    Food toEntity(FoodRequest request);
}
