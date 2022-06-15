package app.api;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

@Component
public class LoginApi extends Api {
    public CompletableFuture<HttpResponse<String>> login(String username, String password) {
        var req = HttpRequest.newBuilder()
                .uri(URI.create(endpoint()))
                .header("Authorization", getBasicAuthenticationHeader(username, password))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return fetchAsync(req);
    }

    private String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    @Override
    protected String subPath() {
        return "login";
    }
}
