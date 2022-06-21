package app.view;

import app.model.Match;
import org.springframework.stereotype.Component;

@Component
public class State {
    private String username;
    private Match currentMatch;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Match getCurrentMatch() {
        return currentMatch;
    }

    public void setCurrentMatch(Match currentMatch) {
        this.currentMatch = currentMatch;
    }
}
