package vttp.backend.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;



import lombok.RequiredArgsConstructor;
import vttp.backend.model.User;
import vttp.backend.service.AuthenticationService;
import vttp.backend.service.EmailService;
import vttp.backend.service.UserService;

@Controller
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final EmailService emailService;
    
    private final AuthenticationService authenticationService;
    
    private final UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User request) {
        Optional<User> opt = userService.findUserByEmail(request.getEmail());
        System.out.println(">>>> TEST Email " + request.getEmail());
        System.out.println(">>>> TEST User " + request.getName());

        if (opt.isPresent()) {
            String message = request.getEmail() + " is already taken";
            return ResponseEntity.badRequest().body(Json.createObjectBuilder().add("message", message).build().toString());
        }

        String email = request.getEmail();
        String subject = "Your account with My Trading Journal has been created!";
        String body = "Thank you for registering with My Trading Journal. Your account has been successfully created.";

        emailService.sendEmail(email, subject, body);

        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    
}
