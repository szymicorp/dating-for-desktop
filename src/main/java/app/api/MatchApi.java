package app.api;

import app.model.Match;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Component
public class MatchApi extends Api {
    public CompletableFuture<Match> getMatch(long id) {
        var req = HttpRequest.newBuilder()
                .header("Cookie", getAuthCookie())
                .uri(URI.create(endpoint() + id))
                .GET()
                .build();

        return fetchAsync(req, new TypeReference<>() {});
    }

    public CompletableFuture<HttpResponse<String>> sendMessage(long id, String content) {
        var req = HttpRequest.newBuilder()
                .header("Cookie", getAuthCookie())
                .uri(URI.create(endpoint() + id + "/send/"))
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .build();

        return fetchAsync(req);
    }

    @Override
    protected String subPath() {
        return "matches/";
    }
}
