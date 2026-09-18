package com.pvt.restaurant.service.dto.request;

import com.pvt.restaurant.domain.enumration.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FoodRequest {
    private String name;
    private Double price;
    private Category category;
    private String image_url;
}
