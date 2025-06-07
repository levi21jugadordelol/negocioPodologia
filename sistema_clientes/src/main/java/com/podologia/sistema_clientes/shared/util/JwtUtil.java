package com.podologia.sistema_clientes.shared.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.podologia.sistema_clientes.usuario.usuario_entity.UsuarioEntity;

import java.util.Date;

public class JwtUtil {

    private static final Algorithm algorithm = Algorithm.HMAC256(secretKey.getSecret());

    public static String generateToken(UsuarioEntity user) {
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
    public static String getUserIdByToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("ATLAcademy")
                .build();
        DecodedJWT decoded = verifier.verify(token);
        String userId = decoded.getClaim("userId").toString();
        return userId;
    }






}
