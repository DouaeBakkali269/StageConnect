package STAGE.stage.payloadRequest;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String role;
}
