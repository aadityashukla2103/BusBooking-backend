package com.cg.config;
import org.springframework.http.HttpMethod;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cg.security.JwtAuthFilter;


@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .cors(cors -> {})
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	            
	         // Swagger Public
	            .requestMatchers(
	                "/swagger-ui/**",
	                "/v3/api-docs/**",
	                "/swagger-ui.html"
	            ).permitAll()

	            // ✅ Auth APIs (public)
	            .requestMatchers("/auth/**", "/generateToken").permitAll()

	            // ✅ PUBLIC APIs (NO LOGIN REQUIRED)
	            .requestMatchers(HttpMethod.GET, "/route").permitAll()
	            .requestMatchers("/schedule/search").permitAll()

	            // 🔒 ADMIN ONLY
	            .requestMatchers(HttpMethod.POST, "/route").hasRole("ADMIN")
//	            .requestMatchers(HttpMethod.POST, "/schedule").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.POST, "/schedule").permitAll()

	            // 🔒 USER (LOGIN REQUIRED)
	            .requestMatchers("/profile").authenticated()
	            .requestMatchers(HttpMethod.POST, "/booking").authenticated()
	            .requestMatchers("/my-bookings").authenticated()

	            // 🔒 Everything else
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration config = new CorsConfiguration();

	    config.setAllowedOrigins(List.of("http://localhost:4200"));
	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    config.setAllowedHeaders(List.of("*"));
	    config.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);

	    return source;
	}
}






