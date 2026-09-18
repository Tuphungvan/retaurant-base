Luồng ứng dụng:

            User - Identity (Phase 2) Authentication
                    ^
                    |
Client -> Spring Cloud Gateway (8080) -> Order (8081) -> Payment (8082) -> Restaurant (8083) -> delivery (8084) (Authorization)

