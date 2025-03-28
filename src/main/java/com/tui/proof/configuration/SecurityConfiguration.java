package com.tui.proof.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for Spring Security.
 * Configures security settings for the application.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserCredentialsConfiguration userCredentialsConfiguration;

    /**
     * Configures HTTP security settings.
     *
     * @param http the HttpSecurity object
     * @throws Exception if an error occurs during configuration
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(a ->
                a.mvcMatchers(HttpMethod.GET, "/orders/search").authenticated().anyRequest().permitAll()
        ).httpBasic();

        http.cors().and().csrf().disable();
        http.headers().frameOptions().disable();
    }

    /**
     * Configures a bean for the password encoder.
     *
     * @return the password encoder bean
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures in-memory authentication with a user.
     *
     * @param auth the AuthenticationManagerBuilder object
     * @throws Exception if an error occurs during configuration
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(userCredentialsConfiguration.getUsername())
                .password(passwordEncoder().encode(userCredentialsConfiguration.getPassword()))
                .roles(userCredentialsConfiguration.getRole());
    }

    /**
     * Configures web security settings.
     *
     * @param web the WebSecurity object
     * @throws Exception if an error occurs during configuration
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "**/h2-console/**");
    }
}