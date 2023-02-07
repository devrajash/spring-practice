package pringboot1st.springboot1.authEndPoints;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthControllerService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> doLogin(@RequestBody AuthReqUser authReqUser) {

        return ResponseEntity.ok(service.login(authReqUser));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createLogin(@RequestBody AuthRegisterReq authRegisterReq) {
        return ResponseEntity.ok(service.register(authRegisterReq));
    }
    @PostMapping("/google")
    public ResponseEntity<AuthResponse> googleLoin(@RequestBody GoogleLoginDto authRegisterReq) {
        return ResponseEntity.ok(service.googleLogin(authRegisterReq));
    }
}
