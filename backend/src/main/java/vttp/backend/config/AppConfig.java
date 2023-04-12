package vttp.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import vttp.backend.repository.SQLRepo;

@Configuration
@RequiredArgsConstructor
public class AppConfig {


    //S3:
    @Value("${spaces.access.key}")
    public String spacesAccessKey; 
    //DO0068DQ8X4N4EEJGH8H
    @Value("${spaces.secret.key}")
    public String spacesSecretKey;
    //fFKAdGIE3OJWOMAib2kgz/MG4PYfRCfODJl/fegb7b4

    //S3
    @Bean
    public AmazonS3 createS3Client() {

        BasicAWSCredentials cred = new BasicAWSCredentials(spacesAccessKey, spacesSecretKey);

        EndpointConfiguration ep = new EndpointConfiguration("sgp1.digitaloceanspaces.com", "spg1");

        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(ep)
            .withCredentials(new AWSStaticCredentialsProvider(cred))
            .build();
    }

    //JWT

    private final SQLRepo sqlRepo;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
              return sqlRepo.findUserByEmail(username)
                .orElseThrow (()-> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Bean
    //DAO = Data access object, is responsible to fetch user details and encode password
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
}
