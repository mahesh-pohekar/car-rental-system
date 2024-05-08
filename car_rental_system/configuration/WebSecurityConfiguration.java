package com.project.car_rental_system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.project.car_rental_system.services.jwt.USerService;
import com.project.car_rental_system.configuration.JwtAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
private final JwtAuthenticationFilter jwtAuthenticationFilter;

private final UserService userService;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
	http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request -> 
	request.requestMatchers("/api/auth/**").permitAll()
	.requestMatchers("/api/admin/**").hasAnyAuthority(UserRole.ADMIN.name()).
	requestMatchers("/api/customer/**").hasAnyAuthority(UserRole.CUSTOMER.name()).
	anyRequest().authenticaed()).sessionManagement(manager -> 
	manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
	authenticationProvider(authenticationProvider()).
	addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	return http.build();
}

@Bean
public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}

@Bean
public AuthenticationProvider authenticationProvider() {
	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	authProvider.setUserDetailsService(userService.userDetailsService());
	authProvider.setPasswordEncoder(passwordEncoder());
	return authProvider;
}

@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
	return config.getAuthenticationManager();
}
}
