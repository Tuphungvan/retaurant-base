# Restaurant
- [x] GET /restaurants
- [x] GET /restaurants/{id}
- [x] POST /restaurants
- [x] PUT /restaurants/{id}
- [x] PATCH /restaurants/{id}/status -> mở / đóng cửa
- [x] DELETE /restaurants/{id}

# Food
- [x] GET /restaurants/{restaurantId}/foods
- [x] GET /foods
- [x] GET /foods/{id}
- [x] POST /restaurants/{restaurantId}/foods
- [x] PUT /foods/{id}
- [x] PATCH /foods/{id}/availability -> còn / hết món
- [x] DELETE /foods/{id}

-- Có thể thay đổi

# Restaurant Orders
- [ ] GET /restaurants/{restaurantId}/orders -> xem danh sách đơn gửi tới quán
- [ ] GET /restaurants/{restaurantId}/orders/{orderId} -> xem chi tiết đơn cần làm
- [ ] POST /restaurants/{restaurantId}/orders/{orderId}/accept -> quán nhận đơn
- [ ] POST /restaurants/{restaurantId}/orders/{orderId}/reject -> quán từ chối đơn
- [ ] POST /restaurants/{restaurantId}/orders/{orderId}/ready -> làm xong, gọi delivery

# Internal
- [ ] POST /foods/batch -> lấy thông tin & giá gốc để tính tiền
- [ ] GET /restaurants/{id}/validate -> kiểm tra quán có mở cửa không
