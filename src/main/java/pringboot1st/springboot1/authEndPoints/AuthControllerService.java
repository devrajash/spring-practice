package pringboot1st.springboot1.authEndPoints;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pringboot1st.springboot1.config.JwtService;
import pringboot1st.springboot1.repository.UserRepository;
import pringboot1st.springboot1.services.GoogleApiAuth;
import pringboot1st.springboot1.user.User;

import java.util.Collection;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthControllerService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final GoogleApiAuth googleApiAuth;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(AuthRegisterReq request) {

        try {
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
           var userData= repository.save(user);
            var jwtToken = jwtService.genarateTokenWithPayload(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .userDetails(userData)
                    .username(user.getUsername())
                    .build();

        } catch (Exception exception) {
            return AuthResponse.builder().msg(exception.getMessage()).build();
        }
    }

    public AuthResponse login(AuthReqUser request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword(), getAuthorities());
        try {
            authenticationManager.authenticate(authenticationToken);
            var user = repository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("no user found"));

            return AuthResponse.builder()
                    .username(user.getUsername())
                    .userDetails(user)
                    .token(jwtService.genarateTokenWithPayload(user))
                    .build();
        } catch (Exception exception) {
            return AuthResponse.builder().msg(exception.getMessage()).build();
        }

    }

    Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public AuthResponse googleLogin(GoogleLoginDto authRegisterReq) {
        try {
            HashMap data = googleApiAuth.verifyToken(authRegisterReq.getAccess_token());
            String userEmail = (String) data.get("email");
            if (userEmail.isEmpty()) {
                return AuthResponse.builder()
                        .msg("Email can not be authenticated")
                        .build();

            }
            var user = repository.findByUsername(userEmail);
            if (!user.isPresent()) {
                var userRegister = User.builder()
                        .firstname(data.get("given_name").toString())
                        .lastname(data.get("family_name").toString())
                        .username(data.get("email").toString())
                        .image(data.get("picture").toString())
                        .password(passwordEncoder.encode("123"))
                        .build();
                var userData = repository.save(userRegister);
                var jwtToken = jwtService.genarateTokenWithPayload(userRegister);
                return AuthResponse.builder()
                        .token(jwtToken)
                        .userDetails(userData)
                        .username(userData.getUsername())
                        .build();
            }
            var userMain = user.orElseThrow();
            return AuthResponse.builder()
                    .token(jwtService.genarateTokenWithPayload(userMain))
                    .userDetails(userMain)
                    .username(userMain.getUsername())
                    .build();
        }catch (Exception e){
            return AuthResponse.builder()
                    .msg(e.getMessage())
                    .build();
        }

    }
}
