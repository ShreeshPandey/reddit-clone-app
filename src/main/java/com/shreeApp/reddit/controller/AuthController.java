package com.shreeApp.reddit.controller;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreeApp.reddit.dto.AuthenticationResponse;
import com.shreeApp.reddit.dto.LoginRequest;
import com.shreeApp.reddit.dto.RefreshTokenRequest;
import com.shreeApp.reddit.dto.RegisterRequest;
import com.shreeApp.reddit.exceptions.SpringRedditException;
import com.shreeApp.reddit.service.AuthService;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

	private static final String REFRESH_TOKEN_DELETED_SUCCESSFULLY = "Refresh Token Deleted Successfully!";
	private final AuthService authService;
	private final RefreshTokenService refreshTokenService;

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) throws SpringRedditException {
		authService.signup(registerRequest);
		return new ResponseEntity<>("User Registration Successful", OK);
	}
	
	@GetMapping("accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) throws SpringRedditException{
		authService.verifyAccount(token);
		return new ResponseEntity<>("Account Succesfully Activated!",OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws InvalidKeyException, SpringRedditException
	{
		return authService.login(loginRequest);
	}
	
	@PostMapping("/refresh/token")
	public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws SpringRedditException {
		return authService.refreshToken(refreshTokenRequest);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body(REFRESH_TOKEN_DELETED_SUCCESSFULLY);
	}
	
	
}
