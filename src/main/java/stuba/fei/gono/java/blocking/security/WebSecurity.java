package stuba.fei.gono.java.blocking.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import stuba.fei.gono.java.blocking.services.EmployeeService;

import static stuba.fei.gono.java.security.SecurityConstants.SIGN_UP_URL;

/***
 * <div class="en"> Class providing security configuration for the endpoints.
 * Sets up authorization using JWTs. Allows
 * unrestricted access to /login endpoint and secures all others with requiring
 * to add Authorization field in HTTP header
 * containing valid JWT with valid credentials of an employee.</div>
 * <div class="sk">Trieda zabezpečuje nastavenie bezpečnosti pre endpoint-y.
 * Povolí prístup na /login a /signup endpoint, a zabezpečí všetky ostatné endpoint-y, vyžadovaním
 * pridania Authorization poľa v HTTP hlavičke, ktoré obsahuje platný JWT s údajmi o zamestnancovi
 * - objektu triedy Employee.</div>
 */
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /***
     * Permits access to SIGN_UP_URL and sets need to authenticate access to any other requests. Adds
     * JWTAuthenticationFilter and JWTAuthorizationFilter to security filters and sets STATELESS session creation policy
     * @see JWTAuthenticationFilter
     * @see JWTAuthorizationFilter
     * @see SessionCreationPolicy
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), getApplicationContext().getBean(EmployeeService.class)))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /***
     * <div class="en">Sets up authorization using the UserDetailsService and adds BCryptPasswordEncoder as
     * password encoder.</div>
     * <div class="sk">Nastaví autorizáciu pomocou inštancie UserDetailsService a nastaví inštanciu
     * BCryptPasswordEncoder ako enkóder hesiel </div>
     * @param auth <div class="en">builder of AuthenticationManager instance.</div>
     *             <div class="sk">tvorca inštancie AuthenticationManager.</div>
     * @throws Exception <div class="en">exception.</div>
     * <div class="sk">výnimka.</div>
     * @see BCryptPasswordEncoder
     * @see UserDetailsService
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /***
     * <div class="en">Allows Cors for any URL source.</div>
     * <div class="sk">Umožní CORS pre všetky URL zdroje.</div>
     * @return <div class="en">UrlBasedCorsConfigurationSource instance.</div>
     * <div class="sk">inštancia triedy UrlBasedCorsConfigurationSource .</div>
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }



}
