package com.pvt.restaurant.service.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RestaurantRequest {
    private String name;
    private String phone;
    private String address;
}
