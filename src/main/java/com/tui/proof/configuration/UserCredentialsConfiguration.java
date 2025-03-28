package com.tui.proof.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for user credentials.
 * Loads user credentials from application properties.
 */
@Configuration
@Data
public class UserCredentialsConfiguration {

    @Value("${user.credentials.username}")
    private String username;

    @Value("${user.credentials.password}")
    private String password;

    @Value("${user.credentials.role}")
    private String role;

}
