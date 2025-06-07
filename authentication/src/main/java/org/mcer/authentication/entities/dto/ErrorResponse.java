package org.mcer.authentication.entities.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(String error, int status, LocalDateTime timestamp, Map<String, String> fieldErrors) {}
