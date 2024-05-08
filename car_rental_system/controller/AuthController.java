package com.project.car_rental_system.controller;


import com.project.car_rental_system.dto.AuthenticationResponse;
import com.project.car_rental_system.dto.SignupRequest;
import com.project.car_rental_system.dto.UserDto;
import com.project.car_rental_system.services.auth.AuthService;

import dto.AuthenticationRequest;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest){
		if(authService.hasCustomerWithEmail(signupRequest.getEmail()))
			return new ResponseEntity<>("Customer already exist with this email", HttpStatus.NOT_ACCEPTABLE);
		UserDto createdCustomerDto = authService.createCustomer(signupRequest);
		if(createCustomerDto == null) return new ResponseEntity<>
		   ("Customer not created, come again later", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws
	       BadCredentialsException,
	       DisbledException,
	       UsernameNotFoundException{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
					authenticationRequest.getPassword()));
		} catch(BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password");
		}
		final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
		Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername()); //doubt
		final String jwt = jwtUtil.generateToken(userDetails);
		AuthenticationResponse authenticationResponse new AuthenticationResponse();
		if(optionalUser.isPresent()) {
			authenticationResponse.setJwt(jwt);
			authenticationResponse.setUserId(optionalUser.get().getId());
			authenticationResponse.setUserRole(optionalUser.get().getUserRole());
		}
	}
}
