package com.pvt.restaurant.service.dto.response;

import com.pvt.restaurant.domain.enumration.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class FoodResponse {
    private UUID id;
    private UUID restaurantId;
    private String name;
    private Double price;
    private Category category;
    private String image_url;
    private Boolean isAvailable;
}
