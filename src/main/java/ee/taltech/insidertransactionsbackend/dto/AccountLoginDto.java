package ee.taltech.insidertransactionsbackend.dto;

import javax.validation.constraints.NotBlank;

public class AccountLoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
