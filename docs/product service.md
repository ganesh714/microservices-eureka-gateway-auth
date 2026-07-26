1. Application.java -> @EnableDiscoveryClient annotation
2. pom.xml -> Add eureka-client dependency
3. application.properties -> Configure eureka server properties
    - server.port=8082
    - eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
4. Run the application

Entity model:
    private Long id;
    private String name;
    private Double price;
    private String description;