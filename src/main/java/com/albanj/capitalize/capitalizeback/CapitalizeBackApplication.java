package com.albanj.capitalize.capitalizeback;

import com.albanj.capitalize.capitalizeback.properties.CorsProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(CorsProperties.class)
public class CapitalizeBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapitalizeBackApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

}
