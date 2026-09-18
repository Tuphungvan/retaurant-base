package com.pvt.restaurant.service.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RestaurantResponse {
    private UUID id;
    private String name;
    private String phone;
    private String address;
    private Boolean isOpen;
}
