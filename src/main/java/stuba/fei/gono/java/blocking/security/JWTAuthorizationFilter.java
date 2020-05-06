package stuba.fei.gono.java.blocking.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import stuba.fei.gono.java.blocking.services.EmployeeService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static stuba.fei.gono.java.security.SecurityConstants.*;

/***
 * <div class="en">Class implementing BasicAuthenticationFilter, using JWT. Checks if the Authorization header
 * field of HTTP request
 * is correctly formed e.g. contains "Bearer JWT", verifies the JWT signature and checks if it contains valid employee
 * credentials.</div>
 * <div class="sk">Trieda implementujúca rozhranie BasicAuthenticationFilter pomocou JWT. Skontroluje
 * či Authorization hlavička HTTP požiadavky obsahuje "Bearer JWT", kde JWT je korektný JWT a skontroluje,
 * či obsahuje správne údaje o zamestnancovi.</div>
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, EmployeeService employeeService) {
        super(authenticationManager);
        this.employeeService= employeeService;
    }

    private  EmployeeService employeeService;
    /***
     * <div class="en">Checks if the HTTP request header has Authorization field that starts
     * with "Bearer " prefix and if so
     * attempts to verify that it contains valid JWT with valid credentials identifying Employee entity with
     * getAuthentication method.</div>
     * <div class="sk">Skontroluje, či Authorization pole HTTP hlavičky začína prefixom "Bearer " a ak
     * áno, pokúsi sa verifikovať či zvyšok pola obsahuje správny JWT s korektnými údajmi, zodpovedajúcimi
     * skutočnej entite triedy Employee.
     * pomocou getAuthentication metódy.</div>
     * @param request <div class="en">HTTP request.</div>
     *                <div class="sk">HTTP požiadavka.</div>
     * @param response response
     * @param chain chain
     * @throws IOException IOException
     * @throws ServletException ServletException
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
     * <div class="en">Extracts the JWT from Authorization field of HTTP request,
     * verifies the signature and checks if it contains
     * valid employee credentials. Uses HMAC and SHA-512.</div>
     * <div class="sk">Extrahuje JWT z Authorization sekcie HTTP požiadavky, verifikuje
     * jeho signatúru a skontroluje, či obsahuje korektné informácie o zamestnancovi.</div>
     * @param request HTTP request
     * @return <div class="en">Token granting authorization to access secured endpoints
     * if JWT with valid credentials identifying Employee entity was provided, null otherwise.</div>
     * <div class="sk">Token povolujúci prístup k zabezpečeným endpoint-om ak bol poskytnutý
     * platný JWT identifikujúici existujúcu entitu triedy Employee, inak null.</div>
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
    {
        String token = request.getHeader(HEADER_STRING);
        if (token != null)
        {
            try{
                String user = JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes()))
                        .build()
                        .verify(token.replace(TOKEN_PREFIX, ""))
                        .getSubject();
                if(user != null)
                {
                    if(employeeService.existsByUsername(user))
                        return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
                    else
                        return null;
                }
                return null;
            } catch (com.auth0.jwt.exceptions.JWTDecodeException ex)
            {
                return null;
            }

        }
        return null;

    }
}
