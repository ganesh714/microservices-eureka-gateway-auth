1. Gateway
2. Eureka Discovery client
application.properties -> Configure eureka server properties
    - server.port=8080
    - eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
3. application.yaml -> 
        spring.cloud.gateway
                        .discovery.locator: enabled:true,lowercase-sesitive-id:true
                        .routes: -id: product-service,uri:lb://product-service,predicates: -path=/products/**
                        ..
                        