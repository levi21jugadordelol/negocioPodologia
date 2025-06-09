package com.podologia.sistema_clientes.shared.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final secretKey key;

  //  private static final Algorithm algorithm = Algorithm.HMAC256(key.getSecret());

    public  String generateToken(UsuarioEntity user) {
        Algorithm algorithm = Algorithm.HMAC256(key.getSecret());

        String token = JWT.create()
                .withIssuer("ATLAcademy")
                .withClaim("userId", user.getIdUsuario())
                .withIssuedAt(new Date())
                .withExpiresAt(getExpiresDate())
                .sign(algorithm);
        return token;
    }

    private static Date getExpiresDate() {
        return new Date(System.currentTimeMillis()
                + (1000L * 60 * 60 * 24 * 14)); // 14 days
    }

    // Decodifica un token JWT y extrae el userId del reclamo.
    public  String getUserIdByToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(key.getSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("ATLAcademy")
                .build();
        DecodedJWT decoded = verifier.verify(token);
        String userId = decoded.getClaim("userId").toString();
        return userId;
    }






}
