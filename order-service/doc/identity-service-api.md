# Dịch vụ Định danh (Identity Service) — Phạm vi API & Phân quyền

## 1. Phạm vi trách nhiệm (Scope)

`order-service` chịu trách nhiệm quản lý:

- Xác thực người dùng (Authentication)
- Thông tin đăng nhập / Mật khẩu (Credentials)
- Trạng thái an toàn của tài khoản (Account security state)
- Danh mục Vai trò (Roles) và Quyền hạn (Permissions) của hệ thống
- Cấp phát, xác thực và vòng đời của Access Token / Refresh Token
- Đăng xuất / Vô hiệu hóa phiên đăng nhập (Session invalidation)
- Các lỗi API liên quan đến bảo mật và phân quyền

Dịch vụ này **KHÔNG** quản lý:
- Dữ liệu hồ sơ cá nhân (Profile: tên hiển thị, avatar, bio... thuộc `restaurant-service`).
- Nghiệp vụ tài nguyên cụ thể (Sách, chương, bài viết, bình luận, báo cáo... thuộc các service tương ứng).

---

## 2. Mô hình phân quyền (RBAC) trong Identity Service

`order-service` đóng vai trò là **Identity Provider (IdP)** trung tâm, quản lý quan hệ:

`User -> Role -> Permission`

Nó lưu trữ và phát hành các vai trò cùng quyền hạn này vào JWT để các Resource Service khác tự kiểm tra.

### Các vai trò cốt lõi (System Roles):
- `USER` (Người dùng thông thường)
- `AUTHOR` (Tác giả)
- `MODERATOR` (Kiểm duyệt viên)
- `ADMIN` (Quản trị viên)

### Các quyền hạn thuộc phạm vi của Identity Service:
Chỉ quản lý các quyền bảo vệ chính các API quản trị tài khoản và phân quyền:

```text
user:read              # Xem thông tin định danh và bảo mật của user
user:ban               # Khóa / Mở khóa tài khoản user
role:read              # Xem danh sách vai trò
role:create            # Tạo vai trò mới
role:update            # Chỉnh sửa vai trò
role:delete            # Xóa vai trò (trừ các vai trò hệ thống)
permission:read        # Xem danh mục quyền hạn
role:permission:assign # Gán / gỡ quyền hạn cho vai trò
user:role:assign       # Gán / gỡ vai trò cho người dùng
```

*(Lưu ý: Các quyền hạn thao tác tài nguyên như sách, bài viết, bình luận... thuộc thẩm quyền định nghĩa và kiểm soát tại các Resource Service tương ứng, Identity Service chỉ đóng vai trò lưu trữ và nhúng vào Token).*

---

## 3. Danh sách API của Identity Service

### A. Xác thực công khai (Public Authentication)

```text
POST /api/v1/auth/register       # Đăng ký tài khoản
POST /api/v1/auth/login          # Đăng nhập lấy cặp Access/Refresh Token
POST /api/v1/auth/refresh        # Cấp mới Access Token từ Refresh Token
POST /api/v1/auth/logout         # Đăng xuất, hủy phiên
```

### B. Bảo mật tài khoản cá nhân (Account Security)

```text
GET  /api/v1/auth/me             # Xem thông tin định danh, roles và quyền của tài khoản hiện tại
POST /api/v1/auth/change-password     # Đổi mật khẩu
POST /api/v1/auth/forgot-password     # Quên mật khẩu (gửi token đặt lại)
POST /api/v1/auth/reset-password      # Đặt lại mật khẩu mới
POST /api/v1/auth/verify-email        # Xác thực email tài khoản
POST /api/v1/auth/resend-verification # Gửi lại email xác thực
```

### C. Quản trị phân quyền (Administrative Authorization Management)

Được bảo vệ bằng các quyền hạn quản trị nội bộ:

```text
# Quản lý tài khoản người dùng
GET    /api/v1/admin/users/{userId}           # Cần quyền: user:read
PATCH  /api/v1/admin/users/{userId}/status    # Cần quyền: user:ban

# Quản lý vai trò (Role)
GET    /api/v1/admin/roles                    # Cần quyền: role:read
POST   /api/v1/admin/roles                    # Cần quyền: role:create
PATCH  /api/v1/admin/roles/{roleId}           # Cần quyền: role:update
DELETE /api/v1/admin/roles/{roleId}           # Cần quyền: role:delete

# Quản lý quyền hạn (Permission)
GET    /api/v1/admin/permissions              # Cần quyền: permission:read
GET    /api/v1/admin/roles/{roleId}/permissions

# Gán Role cho User
POST   /api/v1/admin/users/{userId}/roles/{roleId}        # Cần quyền: user:role:assign
DELETE /api/v1/admin/users/{userId}/roles/{roleId}        # Cần quyền: user:role:assign

# Gán Permission cho Role
POST   /api/v1/admin/roles/{roleId}/permissions/{permissionId}    # Cần quyền: role:permission:assign
DELETE /api/v1/admin/roles/{roleId}/permissions/{permissionId}    # Cần quyền: role:permission:assign
```

---

## 4. Chuẩn cấu trúc JWT (JWT Contract)

Sử dụng thuật toán ký bất đối xứng `RS256` (Private Key tại Identity Service, Public Key chia sẻ cho các Resource Services):

```json
{
  "iss": "https://identity.pvtteria.com",
  "sub": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "aud": "pvtteria-api",
  "iat": 1725780000,
  "exp": 1725780900,
  "jti": "d3b07384-d113-40e1-95bd-44c13a0c5f2b",
  "session_id": "b1a134f0-466d-4952-b52e-68bcddb1c901",
  "roles": [
    "USER",
    "AUTHOR"
  ],
  "authorities": [
    "delivery:read",
    "delivery:create",
    "review:create"
  ]
}
```
