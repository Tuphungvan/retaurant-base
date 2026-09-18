package com.pvt.restaurant.service.impl;

import com.pvt.restaurant.domain.Restaurant;
import com.pvt.restaurant.repository.RestaurantRepository;
import com.pvt.restaurant.service.RestaurantService;
import com.pvt.restaurant.service.dto.request.RestaurantRequest;
import com.pvt.restaurant.service.dto.response.RestaurantResponse;
import com.pvt.restaurant.service.mapper.RestaurantMapper;
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
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepo;
    private final RestaurantMapper resMapper;

    @Override
    @Transactional
    public RestaurantResponse createRestaurant(RestaurantRequest request) {
        if (restaurantRepo.existsByNameAndPhoneAndAddressAndDeletedAtIsNull(request.getName(), request.getPhone(), request.getAddress())) {
            throw new BussinessException(ErrorCode.restaurant_active_exists);
        }
        Restaurant restaurant = resMapper.toEntity(request);
        restaurantRepo.save(restaurant);
        return resMapper.toDto(restaurant);
    }

    @Override
    @Transactional
    public RestaurantResponse updateRestaurant(UUID id, RestaurantRequest request) {
        Restaurant restaurant = restaurantRepo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BussinessException(ErrorCode.restaurant_active_not_found_by_id));
        restaurant.setName(request.getName());
        restaurant.setPhone(request.getPhone());
        restaurant.setAddress(request.getAddress());
        restaurantRepo.save(restaurant);
        return resMapper.toDto(restaurant);
    }

    @Override
    @Transactional
    public RestaurantResponse closeOpenRestaurant(UUID id, Boolean isOpen) {
        Restaurant restaurant = restaurantRepo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BussinessException(ErrorCode.restaurant_active_not_found_by_id));
        restaurant.setIsOpen(isOpen);
        restaurantRepo.save(restaurant);
        return resMapper.toDto(restaurant);
    }

    @Override
    @Transactional
    public Boolean deleteRestaurant(UUID id) {
        Restaurant restaurant = restaurantRepo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BussinessException(ErrorCode.restaurant_active_not_found_by_id));
        restaurant.setDeletedAt(Instant.now());
        restaurantRepo.save(restaurant);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantResponse> getRestaurantPage(Pageable pageable) {
        return restaurantRepo.findAllByDeletedAtIsNull(pageable).map(resMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantResponse findRestaurant(UUID id) {
        Restaurant restaurant = restaurantRepo.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BussinessException(ErrorCode.restaurant_active_not_found_by_id));
        return resMapper.toDto(restaurant);
    }
}
