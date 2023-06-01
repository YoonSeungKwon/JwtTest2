package yoon.test.jwtTest2.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "nsadlnaiow123ib2ikjbk2i3b1k3bk1iu2i3n1";
    private long exp = 60 * 60 * 1000l;                        //60 Min

    private final UserDetailsService userDetailsService;

    public String createToken(String id, String password){

        //Registered Claim
        Claims claims = Jwts.claims()
                .setSubject("access Token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+exp));

        //Private Claim
        claims.put("id", id);
        claims.put("password", password);

        //Build Token
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")                //Header
                .setClaims(claims)                                      //Payload
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)         //Signature
                .compact();

        return jwt;

    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getId(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getId(String token){                                     //get Id From Token

        return (String)Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token).getBody().get("id");
    }

    public boolean validateToken(String token){                             //Validate Check
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e){
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request){                 //get Token From Request
        return request.getHeader("X-AUTH-TOKEN");
    }
}
