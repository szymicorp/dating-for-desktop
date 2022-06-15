package app.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public abstract class Api {
    @Value("${server.address}")
    private String SERVER_URI;

    private static String authCookie;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    protected abstract String subPath();

    public <T> CompletableFuture<T> fetchAsync(HttpRequest request, Class<T> returnType) {
        return client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(body -> {
                    try {
                        return mapper.readValue(body, returnType);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    public <T> CompletableFuture<T> fetchAsync(HttpRequest request, TypeReference<T> returnType) {
        return client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(body -> {
                    try {
                        return mapper.readValue(body, returnType);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    public CompletableFuture<HttpResponse<String>> fetchAsync(HttpRequest request) {
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public <T> T fetch(HttpRequest request, TypeReference<T> returnType) throws IOException, InterruptedException {
        var body =  client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        try {
            return mapper.readValue(body, returnType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setAuthCookie(String cookie) {
        authCookie = cookie;
    }

    public static String getAuthCookie() {
        return authCookie;
    }

    public String endpoint() {
        return SERVER_URI + subPath();
    }
}
