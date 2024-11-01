package ch.swisssign.ra.app.jws;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.SECONDS;

import ch.swisssign.ra.app.config.ApiConfiguration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public final class JWSGenerator {

  private static final String TYPE = "JWT";

  private final ApiConfiguration configuration;

  public SignedJWT generateJWS() throws InvalidKeyException {
    final var key = Keys.hmacShaKeyFor(configuration.secret().getBytes(UTF_8));

    final var now = Instant.now();

    final var expirationTime = now.plusSeconds(configuration.tokenLifetime().toSeconds());

    final var jws =
        Jwts.builder()
            .subject(configuration.username())
            .issuer(configuration.issuer())
            .header()
            .type(TYPE)
            .and()
            .audience()
            .add(configuration.audience())
            .and()
            .expiration(Date.from(expirationTime))
            .notBefore(Date.from(now.minusSeconds(SECONDS.toSeconds(10))))
            .issuedAt(Date.from(now))
            .signWith(key, Jwts.SIG.HS256)
            .compact();

    return new SignedJWT(jws, expirationTime);
  }
}
