package vttp.backend.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor //Will create the constructor based on the private final ...
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        //Intercepts the HTTP connection
            @NonNull HttpServletRequest request, 
            @NonNull HttpServletResponse response, 
            @NonNull FilterChain filterChain
            ) throws ServletException, IOException {

        
        //JWT is in the HTTP Header. This is to extract the authentication
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String email;

        //Check if token is present
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        //Extract the token ["Bearer " is 6 so will start with 7]
        jwtToken = authHeader.substring(7);
        //Extract the email
        email = jwtService.extractUsername(jwtToken);

        
        //Check if user has been authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //Override UserDetails method using AppConfig by getting user from SQL
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            //Check if user is valid
            if (jwtService.isTokenValid(jwtToken, userDetails)){

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Update the Security Context Holder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
