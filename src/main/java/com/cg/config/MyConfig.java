package com.cg.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@SecurityScheme(
        name = "BearerAuth", // Name for your security scheme
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
    )
public class MyConfig {

	@Autowired
	private DataSource dataSource;
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	/*@Bean
	public UserDetailsService userDetailsService() {

		UserDetails hemanth = User.builder().username("hemanth")
				.password(passwordEncoder().encode("pass123"))
				.roles("USER").build();

		UserDetails admin = User.builder().username("admin")
				.password(passwordEncoder().encode("admin"))
				.roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(hemanth, admin);
	}*/
	
	

    
    
    
    @Bean
    public UserDetailsService userDetailsService() {
    	JdbcDaoImpl obj = new JdbcDaoImpl();
        obj.setJdbcTemplate(new JdbcTemplate(dataSource));
    	return obj;
    }
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}








