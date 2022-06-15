package app.api;

import com.fasterxml.jackson.core.type.TypeReference;
import app.model.Match;
import app.model.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class UserApi extends Api {
    public CompletableFuture<User> getUser(int id) {
        var req = HttpRequest.newBuilder()
                .uri(URI.create(endpoint() + id))
                .GET()
                .build();

        return fetchAsync(req, User.class);
    }

    public CompletableFuture<List<Match>> getUserMatches(int id) {
        var req = HttpRequest.newBuilder()
                .uri(URI.create(endpoint() + id + "matches/"))
                .GET()
                .build();

        return fetchAsync(req, new TypeReference<>() {});
    }

    public List<User> getUnseenUsers() throws IOException, InterruptedException {
        var req = HttpRequest.newBuilder()
                .header("Cookie", getAuthCookie())
                .uri(URI.create(endpoint() + "unseen/"))
                .GET()
                .build();

        return fetch(req, new TypeReference<>() {});
    }

    private void userAction(int userId, String suffix) {
        var req = HttpRequest.newBuilder()
                .header("Cookie", getAuthCookie())
                .uri(URI.create(endpoint() + userId + suffix))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        fetchAsync(req);
    }

    public void like(int userId) {
        userAction(userId, "/like");
    }

    public void dislike(int userId) {
        userAction(userId, "/dislike");
    }

    @Override
    protected String subPath() {
        return "users/";
    }
}
