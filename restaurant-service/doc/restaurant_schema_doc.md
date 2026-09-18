# table restaurants
- id pk
- name
- phone
- address
- is_open (boolean, default true)
- deleted_at

-> unique: (name, phone, address) where deleted_at is null

# table foods
- id pk
- restaurant_id fk -> restaurants(id)
- name
- price
- category
- image_url
- is_available (boolean, default true)
- deleted_at

-> unique: (restaurant_id, name) where deleted_at is null
