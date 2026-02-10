package com.demo.auth_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
		httpSecurity.csrf((csrf) -> {
			csrf.disable();
		})
		.authorizeHttpRequests((authorize) -> {
			authorize.requestMatchers("/open", "/auth/**").permitAll()
				.anyRequest().authenticated();
		})
		.addFilterBefore(new AuthFilter(), UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}
}