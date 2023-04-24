package vttp.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JWTSecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/auth/**", "/", "/index.html", "/favicon.ico", "/manifest.json", "/images/**", "/assets/**",
            "/3rdpartylicenses.txt", "main.126ca94bf86bdd8c.js", "polyfills.794d7387aea30963.js", "runtime.0ff8b5c7db2b67dc.js",
            "styles.c3006ee056c356fb.css", "layers.ef6db8722c2c3f9a.png", "layers-2x.9859cd1231006a4a.png",
            "marker-icon.d577052aa271e13f.png", "stocks.abdf9cf2d1e461b4.jpg"
            ) //For white listing
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
    
}
