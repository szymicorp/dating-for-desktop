package app.view;

public enum View {
    LOGIN("Log in", "Login"),
    CARDS("Browse people", "Cards"),
    MATCHES("Your matches", "Matches");

    private final String title;
    private final String fxml;

    View(String title, String fxml) {
        this.title = title;
        this.fxml = fxml;
    }

    public String getTitle() {
        return title;
    }

    public String getFxml() {
        return "/app/view/fxml/" + fxml + ".fxml";
    }
}
