package hexlet.code.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        var authentication = new UsernamePasswordAuthenticationToken(
            authRequest.getUsername(), authRequest.getPassword());

        authenticationManager.authenticate(authentication);

        var token = jwtUtils.generateToken(authRequest.getUsername());
        return token;
    }
}
