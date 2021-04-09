package com.albanj.capitalize.capitalizeback.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {

    private String origins;
}
