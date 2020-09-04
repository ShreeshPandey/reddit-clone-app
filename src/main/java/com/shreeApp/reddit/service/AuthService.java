package com.shreeApp.reddit.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shreeApp.reddit.dto.AuthenticationResponse;
import com.shreeApp.reddit.dto.LoginRequest;
import com.shreeApp.reddit.dto.RegisterRequest;
import com.shreeApp.reddit.exceptions.SpringRedditException;
import com.shreeApp.reddit.model.NotificationEmail;
import com.shreeApp.reddit.model.User;
import com.shreeApp.reddit.model.VerificationToken;
import com.shreeApp.reddit.repository.UserRepository;
import com.shreeApp.reddit.repository.VerificationTokenRepository;
import com.shreeApp.reddit.security.JwtProvider;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvder;

	public void signup(RegisterRequest registerRequest) throws SpringRedditException {

		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreatedDate(Instant.now());
		user.setEnabled(false);

		userRepository.save(user);

		String token = generateVerificationToken(user);

		mailService.sendMail(new NotificationEmail("Please Activate your Account", user.getEmail(),
				"Thank you for signing up to Spring Reddit, "
						+ "please click on the below url to activate your account : "
						+ "http://localhost:8081/api/auth/accountVerification/" + token));
	}
	
	@Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);

		verificationTokenRepository.save(verificationToken);
		return token;
	}

	public void verifyAccount(String token) throws SpringRedditException {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);		
		fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));
	}

	private void fetchUserAndEnable(VerificationToken verificationToken) throws SpringRedditException {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new SpringRedditException("User not Found with username: " + username));
		user.setEnabled(true);
		userRepository.save(user);
	}

	public AuthenticationResponse login(LoginRequest loginRequest) throws InvalidKeyException, SpringRedditException {
	Authentication authentication =	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvder.generateToken(authentication);
		return new AuthenticationResponse(token,loginRequest.getUsername());
	}

	public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
