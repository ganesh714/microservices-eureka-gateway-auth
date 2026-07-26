1. Application.java -> @EnableEurekaClient annotation
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