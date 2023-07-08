# Phone Booking Application
Simple implementation of:
* Booking phone
* Returning booked phone
* Listing available devices

# Tech stack
* Kotlin
* Springboot
* MySql (DB)

# FONOAPI
[FONOAPI](https://fonoapi.freshpixl.com/) is down hence using data from [https://github.com/ilyasozkurt/mobilephone-brands-and-models/blob/master/devices.sql](https://github.com/ilyasozkurt/mobilephone-brands-and-models/blob/master/devices.sql) instead.

# Running the Application
### Prerequisites
* JDK 17
* Docker

### Running the Application
Note: In this example, mysql DB is running in local host (make sure its running), you can launch a separate docker for this and use docker compose instead if needed
* ```docker run -p 8081:8081 --env SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/phone --env SPRING_DATASOURCE_USERNAME=admin --env SPRING_DATASOURCE_PASSWORD=admin phone-booking```

### API endpoints
Launch [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) to see the whole API endpoints

### Monitoring
Launch the Prometheus, Grafana and Cadvisor via docker compose ```docker compose up```