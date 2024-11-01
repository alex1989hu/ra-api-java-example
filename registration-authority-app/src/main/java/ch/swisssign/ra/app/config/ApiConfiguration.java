package ch.swisssign.ra.app.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "ra.app.api", ignoreUnknownFields = false)
@Validated
public record ApiConfiguration(
    @NotNull @NotBlank String audience,
    @NotNull @NotBlank String client,
    @NotNull @NotBlank String issuer,
    @NotNull @NotBlank String username,
    @NotNull @NotBlank String secret,
    @NotNull @NotBlank String basePath,
    @NotNull @DurationMin(seconds = 5) Duration tokenLifetime) {}
