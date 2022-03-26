package account.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordRequest {
    @JsonProperty(value = "new_password")
    String password;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "password='" + password + '\'' +
                '}';
    }
}
