package hexlet.code.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hexlet.code.dto.AuthRequest;
import hexlet.code.util.JWTUtils;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthenticationController {

    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "User authentication")
    @ApiResponse(responseCode = "200", description = "You have successfully authenticated")
    @PostMapping("/login")
    public String create(
            @Parameter(description = "Your login and password")
            @RequestBody AuthRequest authRequest) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            var token = jwtUtils.generateToken(authentication.getName());
            return token;
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }
}
