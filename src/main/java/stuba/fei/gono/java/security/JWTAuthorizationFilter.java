package stuba.fei.gono.java.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static stuba.fei.gono.java.security.SecurityConstants.*;

/***
 * Class implementing BasicAuthenticationFilter, using JWT. Checks if the Authorization header field of HTTP request
 * is correctly formed e.g. contains "Bearer JWT", decrypts the JWT and checks if it contains valid employee
 * credentials.
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /***
     * Checks if the HTTP request header has Authorization field that starts with "Bearer " prefix and if so
     * attempts to verify that it contains valid JWT with valid employee credentials with getAuthentication method.
     * @param request HTTP request
     * @param response response
     * @param chain chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(HEADER_STRING);

        if(header == null || !header.startsWith(TOKEN_PREFIX))
        {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

        //super.doFilterInternal(request, response, chain);
    }

    /***
     * Extracts the JWT from Authorization field of HTTP request, decrypts it and verifies if it contains
     * valid employee credentials.
     * @param request HTTP request
     * @return Token granting authorization to access secured endpoints if JWT with valid credentials was provided,
     * null otherwise.
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
    {
        String token = request.getHeader(HEADER_STRING);
        if (token != null)
        {
            String user = JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if(user != null)
            {
                return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
            }
            return null;
        }
        return null;

    }
}
