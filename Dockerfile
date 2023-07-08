FROM eclipse-temurin:17.0.7_7-jdk
WORKDIR /app
COPY target/phone-booking-0.0.1-SNAPSHOT.jar phone-booking.jar
EXPOSE 8081
CMD ["java", "-jar", "phone-booking.jar"]