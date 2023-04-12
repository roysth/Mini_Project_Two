package vttp.backend.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.RequiredArgsConstructor;
import vttp.backend.config.JWTService;
import vttp.backend.model.Role;
import vttp.backend.model.User;
import vttp.backend.repository.SQLRepo;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final SQLRepo sqlRepo;

    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;


    public String register(User user){

        //Password is akready inside the user. Just need to encode it
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
    
        sqlRepo.registerUser(user);

        //Generate the jwt upon successful registration
        String jwtToken = jwtService.generateToken(user);

        JsonObject object = Json.createObjectBuilder().add("token", jwtToken).build();

        return object.toString();
    }

    public String authenticate(User user){

        //Authenticate user
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            user.getEmail(), user.getPassword())
        );
        Optional<User> opt = sqlRepo.findUserByEmail(user.getEmail());
        String jwtToken = jwtService.generateToken(opt.get());

        return Json.createObjectBuilder().add("token", jwtToken).build().toString();
    }
    
}
