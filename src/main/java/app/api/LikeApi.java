package app.api;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Component
public class LikeApi extends Api {

    public CompletableFuture<HttpResponse<String>> giveLike(int senderId, int receiverId) {
        var req = HttpRequest.newBuilder()
                .uri(URI.create(endpoint() + receiverId + "?loggedId=" + senderId))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return fetch(req);
    }

    @Override
    protected String subPath() {
        return "likes/";
    }
}
