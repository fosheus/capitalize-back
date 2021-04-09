package com.albanj.capitalize.capitalizeback.security;

import java.util.Arrays;

import com.albanj.capitalize.capitalizeback.properties.CorsProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebSecurityCorsConfiguration {

	private CorsProperties corsProperties;

	@Autowired
	public WebSecurityCorsConfiguration(CorsProperties corsProperties) {
		this.corsProperties = corsProperties;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				log.info("CORS : " + corsProperties.getOrigins());
				if (!corsProperties.getOrigins().isEmpty()) {
					registry.addMapping("/**").allowedOrigins(corsProperties.getOrigins().split(","))
							.allowedHeaders("*")
							.allowedMethods(HttpMethod.OPTIONS.name(), HttpMethod.DELETE.name(), HttpMethod.GET.name(),
									HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.PATCH.name())
							.exposedHeaders(
									"Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
											+ "Content-Type, Access-Control-Request-Method, Custom-Filter-Header");
				} else {
					log.warn("Aucune configuration CORS : " + corsProperties.getOrigins());
				}
			}
		};
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		log.info("CORS : " + corsProperties.getOrigins());
		CorsConfiguration configuration = new CorsConfiguration();
		if (!corsProperties.getOrigins().isEmpty()) {
			configuration.setAllowedOrigins(Arrays.asList(corsProperties.getOrigins().split(",")));
		} else {
			log.warn("Aucune configuration CORS : " + corsProperties.getOrigins());
		}
		configuration.setAllowedMethods(Arrays.asList(HttpMethod.OPTIONS.name(), HttpMethod.DELETE.name(),
				HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.PATCH.name()));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.addExposedHeader(
				"Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
						+ "Content-Type, Access-Control-Request-Method, Custom-Filter-Header");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
