package tp.eni_store.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tp.eni_store.bo.User;
import tp.eni_store.dao.user.IDAOUser;

import java.security.Key;
import java.util.Date;

import static tp.eni_store.service.ServicesConstants.*;

@Component
public class AuthService {

    private final IDAOUser daoUser;

    public AuthService(IDAOUser daoUser) {
        this.daoUser = daoUser;
    }

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    private Key getSecretKey() {

        // Convertir un string en base64
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        // Convertir une base 64 en Key
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

    public ServiceResponse<String> createToken(String email, String password) {

        User loggedPerson = daoUser.selectPersonByLogin(email, password);

        if(loggedPerson == null){
            return ServiceHelper.buildResponse(CD_ERR_NOT_FOUND, "Aucun utilisateur connecté", null);
        }

        Date tokenLifetime = new Date(System.currentTimeMillis() + ((1000 * 60 * 60) * 1));

        // Le code pour générer un token
        String token = Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(tokenLifetime)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();

        return ServiceHelper.buildResponse(CD_SUCCESS_DEFAULT, "Token généré avec succès", token);
    }

    public ServiceResponse<Boolean> checkToken(String token) {

        //Error: 1 - Si empty
        if(token.isEmpty()) {

            ServiceHelper.buildResponse(CD_ERR_INVALID, "Erreur: Token vide", false);
        }

        // ATTENTION SELON LE CAS LE TOKEN EST SUFFIXE D'UN DISCRIMINANT
        // ex Bearer montoken
        // je dois ignorer les 7 premiers caractère
        token = token.substring(7);

        try {
            //Outil pour déchiffrer le token
            JwtParser jwtParser = Jwts.parser().setSigningKey(getSecretKey()).build();
            //Récupérer les claims de mon token (=> toutes les infos)
            Claims claims = jwtParser.parseClaimsJws(token).getBody();

            //Récupérer la date d'expiration
            //1 : Version abstraite (couplage faible)
            //Function<Claims, Date> expirationFunction = Claims::getExpiration;
            //Date expirationDate = expirationFunction.apply(claims);
            //2 : Version explicite (couplage fort)
            Date expirationDate = claims.getExpiration();

        } catch (Exception ex) {

            if(ex instanceof ExpiredJwtException) {
                return ServiceHelper.buildResponse(CD_ERR_INVALID, "Erreur: Token expiré", false);
            }

            if(ex instanceof MalformedJwtException) {
                return ServiceHelper.buildResponse(CD_ERR_INVALID, "Erreur: Token malformé", false);
            }

            return ServiceHelper.buildResponse(CD_ERR_INVALID, "Erreur inconnue", false);
        }


        return ServiceHelper.buildResponse(CD_SUCCESS_DEFAULT, "Token valide", true);
    }

    public Claims getClaimsFromToken(String token){
        JwtParser jwtParser = Jwts.parser().setSigningKey(getSecretKey()).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();
        return claims;
    }

    public String getEmailFromToken(String token){
        String email = getClaimsFromToken(token).getSubject();

        return email;
    }
}
