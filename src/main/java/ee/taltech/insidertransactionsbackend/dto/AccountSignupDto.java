package ee.taltech.insidertransactionsbackend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AccountSignupDto {

    @NotBlank
    @Email
    @Size(max = 255)
    private String username;

    @NotBlank
    @Size(min = 8, max = 255)
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
