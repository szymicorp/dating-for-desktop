package app.api;

import com.fasterxml.jackson.core.type.TypeReference;
import app.model.Match;
import app.model.User;
import org.springframework.stereotype.Component;

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

        return fetch(req, User.class);
    }

    public CompletableFuture<List<Match>> getUserMatches(int id) {
        var req = HttpRequest.newBuilder()
                .uri(URI.create(endpoint() + id + "/matches"))
                .GET()
                .build();

        return fetch(req, new TypeReference<>() {});
    }

    @Override
    protected String subPath() {
        return "users/";
    }
}
