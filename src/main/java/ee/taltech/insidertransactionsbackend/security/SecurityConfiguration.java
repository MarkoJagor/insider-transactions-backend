package ee.taltech.insidertransactionsbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ee.taltech.insidertransactionsbackend.security.jwt.AuthEntryPointJwt;
import ee.taltech.insidertransactionsbackend.security.jwt.AuthTokenFilter;
import ee.taltech.insidertransactionsbackend.security.jwt.JwtUtils;
import ee.taltech.insidertransactionsbackend.service.AccountDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AccountDetailsServiceImpl accountDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    private final JwtUtils jwtUtils;

    @Autowired
    public SecurityConfiguration(final AccountDetailsServiceImpl accountDetailsService,
            final AuthEntryPointJwt unauthorizedHandler, final JwtUtils jwtUtils) {
        this.accountDetailsService = accountDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public SecretKey generateSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }*/

    @Bean
    public AuthTokenFilter authJwtTokenFilter(){
        return new AuthTokenFilter(this.jwtUtils, this.accountDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //FOR AUTHENTICATION
    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailsService).passwordEncoder(getPasswordEncoder());
    }

    //FOR AUTHORIZATION
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/api/v1/account/**").permitAll()
                .antMatchers("/api/v1/transactions/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.
                addFilterBefore(this.authJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
