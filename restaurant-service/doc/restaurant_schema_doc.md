# table restaurants
- id pk
- name
- phone
- address

-> unique: (name, phone, address)

# table foods
- id pk
- name
- price
- category
- image_url

# table restaurant_food
- ( restaurant_id , food_id ) pk