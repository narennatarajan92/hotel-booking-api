package com.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JsonLoggingFilter implements Filter {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Path LOG_PATH = Paths.get("target/logs", "logfile.txt");

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        Instant requestTime = Instant.now();
        Response response = ctx.next(requestSpec, responseSpec);
        Instant responseTime = Instant.now();

        logRequestAndResponse(requestSpec, response, requestTime, responseTime);
        return response;
    }

    private void logRequestAndResponse(FilterableRequestSpecification requestSpec,
                                       Response response,
                                       Instant requestTime,
                                       Instant responseTime) {
        try {
            Files.createDirectories(LOG_PATH.getParent());
            writeLogToFile(createLogJson(requestSpec, response, requestTime, responseTime));
        } catch (IOException e) {
            e.printStackTrace(); // or use a fallback logger
        }
    }

    private ObjectNode createLogJson(FilterableRequestSpecification requestSpec,
                                     Response response,
                                     Instant requestTime,
                                     Instant responseTime) {

        ObjectNode logEntry = mapper.createObjectNode();

        ObjectNode requestNode = logEntry.putObject("request");
        ZonedDateTime cetTime = requestTime.atZone(ZoneId.of("CET"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedTime = cetTime.format(formatter);
        requestNode.put("timestamp", formattedTime);
        requestNode.put("method", requestSpec.getMethod());
        requestNode.put("uri", requestSpec.getURI());
        requestNode.put("headers", requestSpec.getHeaders().toString());
        requestNode.put("body", requestSpec.getBody() != null ? requestSpec.getBody() : "");

        cetTime = responseTime.atZone(ZoneId.of("CET"));
        formattedTime = cetTime.format(formatter);
        ObjectNode responseNode = logEntry.putObject("response");
        responseNode.put("timestamp", formattedTime);
        responseNode.put("statusCode", response.getStatusCode());
        responseNode.put("statusLine", response.getStatusLine());
        responseNode.put("headers", response.getHeaders().toString());
        responseNode.put("body", response.getBody().asPrettyString());

        return logEntry;
    }

    private void writeLogToFile(ObjectNode logEntry) throws IOException {
        try (FileWriter writer = new FileWriter(LOG_PATH.toFile(), true)) {
            writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logEntry));
            writer.write(System.lineSeparator());
        }
    }
}
