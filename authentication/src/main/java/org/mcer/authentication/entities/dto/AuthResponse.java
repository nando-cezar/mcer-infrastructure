package org.mcer.authentication.entities.dto;

public record AuthResponse(int status, String message, String token) {}
