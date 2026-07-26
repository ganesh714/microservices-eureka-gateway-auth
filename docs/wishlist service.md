1. Application.java -> @EnableDiscoveryClient annotation, @EnableFeignClients annotation to connect to other services
2. pom.xml -> Add eureka-client dependency
3. application.properties -> Configure eureka server properties
    - server.port=8084
    - eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
4. Run the application

Entity model:
    private Long id;
    private Long userId;
    private Long productId;
    private String productName; 
    private Double productPrice;  

client package:
    ProductClient (@FeignClient annotation):
        getProductById with end point "/products/{id}" to fetch product details from Product Service to wishlist service

ProductDto: help to fetch prodcut in ProductClient