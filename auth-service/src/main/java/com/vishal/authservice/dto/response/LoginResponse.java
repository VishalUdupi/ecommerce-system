package com.vishal.authservice.dto.response;

import com.vishal.authservice.enums.Role;

public record LoginResponse(
        String token,
        Role role
) {}
