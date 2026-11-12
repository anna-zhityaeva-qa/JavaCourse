package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.util.Base64;

public class RestApiBuilder {

    public static final String BASE_URI = "http://localhost:8080";
    public static final String LOGIN = "admin";
    public static final String PASSWORD = "secret123";

    public static RequestSpecification getBuilder() {
        // Base64 кодирование: admin:secret123
        String credentials = LOGIN + ":" + PASSWORD;
        String encodedCredentials = Base64.getEncoder()
                .encodeToString(credentials.getBytes());

        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .addHeader("Authorization", "Basic " + encodedCredentials)
                .addHeader("Content-Type", "application/json")
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }
}