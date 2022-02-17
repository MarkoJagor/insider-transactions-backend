package ee.taltech.insidertransactionsbackend.response;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;

    public JwtResponse(final String token, final Long id, final String username) {
        this.token = token;
        this.id = id;
        this.username = username;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
