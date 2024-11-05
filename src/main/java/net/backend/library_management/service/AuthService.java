package net.backend.library_management.service;

import net.backend.library_management.dto.JwtAuthResponse;
import net.backend.library_management.dto.LoginDto;
import net.backend.library_management.dto.RegisterDto;

public interface AuthService {

    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);

}
