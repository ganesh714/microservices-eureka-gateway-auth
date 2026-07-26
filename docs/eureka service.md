1. Application.java -> @EnableEurekaServer annotation
2. pom.xml -> Add eureka-server dependency
3. application.properties -> Configure eureka server properties
    - server.port=8761
    - eureka.client.register-with-eureka=false
    - eureka.client.fetch-registry=false
    - eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
    - eureka.instance.hostname=localhost
4. Run the application

No need to register the application(like auth service,...) , it automatically registers itself to the eureka server when those services have @EnableEurekaClient annotation and eureka-client dependency.