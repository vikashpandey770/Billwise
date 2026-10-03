package SpringBoot.Billwise.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import SpringBoot.Billwise.dto.LoginRequest;
import SpringBoot.Billwise.dto.LoginResponse;
import SpringBoot.Billwise.entity.Admin;
import SpringBoot.Billwise.repository.AdminRepository;
import SpringBoot.Billwise.security.JwtService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse  login(
            @RequestBody LoginRequest request) {
        Admin admin = adminRepository
                .findByEmail(request.getEmail())
                .orElse(null);
        if (admin == null) {
            throw new RuntimeException("Admin not found");
        }
        if (!passwordEncoder.matches(
                request.getPassword(),
                admin.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token =
                jwtService.generateToken(admin.getEmail());
        return new LoginResponse(
                "Login successful",
                token
        );
    }
}