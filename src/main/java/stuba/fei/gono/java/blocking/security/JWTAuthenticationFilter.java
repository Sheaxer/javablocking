package stuba.fei.gono.java.blocking.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import stuba.fei.gono.java.pojo.Employee;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static stuba.fei.gono.java.security.SecurityConstants.*;

/***
 * <div class="en">Class that implements UsernamePasswordAuthenticationFilter for authentication
 * for access to ReportedOverlimitTransaction REST resource for bank employees.
 * Allows authentication with username and password for employees. Checks the provided username
 * and password and if valid issues a JWT that enables access to the resources.</div>
 * <div class="sk">Trieda, ktorá implementuje rozhranie UsernamePasswordAuthenticationFilter
 * na overenie prístupu k REST zdrojom pre bankových zamestnancov. Skontroluje či poslané
 * používateľské meno a heslo sú korektný pár a ak áno, vydá JWT ktorý umožnuje prístup k zdrojom.
 * </div>
 * @see com.auth0.jwt.JWT
 * @see UsernamePasswordAuthenticationFilter
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    /***
     * <div class="en">Checks if HTTP request contains username and password fields
     * and attempts authentication by checking if employee with the same username and password exists.</div>
     * <div class="sk">Skontroluje či HTTP požiadavka obsahuje username a password polia a pokúsi sa
     * vykonať overenie totožnosti skontrolovaním či existuje entita s rovnakým používateľským menom a
     * heslom.</div>
     * @param request <div class="en">HTT request</div>
     *                <div class="sk">HTTP požiadavka</div>
     * @param response <div class="en">HTTP response</div>
     *                 <div class="sk">HTTP odpoveď</div>
     * @return Authentication
     * @throws AuthenticationException <div class="en">exception.</div>
     * <div class="sk">výnímka.</div>
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try
        {
            Employee creds = new ObjectMapper()
                    .readValue(request.getInputStream(), Employee.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),creds.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * <div class="en">Generates JWT upon successful authentication and adds it to Authorization
     * header of HTTP response</div>
     * <div class="sk">Generuje JWT po úspešnom overení totožnosti a pridá ho do Authorization sekcie hlavičky
     * HTTP odpovede.</div>
     * @param req <div class="en">server request</div>
     *            <div class="sk">požiadavka na server</div>
     * @param res <div class="en">server response</div>
     *            <div class="sk">odpoveď servera</div>
     * @param chain <div class="en">chain of filters of request</div>
     *              <div class="sk">reťaz filterov server požiadavky na server</div>
     * @param auth <div class="en">authentication token</div>
     *             <div class="sk">overovací token.</div>
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_LENGTH))
                .sign(HMAC512(SECRET_KEY.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}