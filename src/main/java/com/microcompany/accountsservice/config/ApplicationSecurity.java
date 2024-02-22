package com.microcompany.accountsservice.config;

import com.microcompany.accountsservice.jwt.JwtTokenFilter;
import com.microcompany.accountsservice.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.microcompany.accountsservice.model.ERole;
import com.microcompany.accountsservice.model.User;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class ApplicationSecurity {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationSecurity.class);

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String enc_password = passwordEncoder.encode("encrypt");

                List<User> usuarios = new ArrayList<>();
                usuarios.add(new User(1, "emailGestor@mail.com", enc_password, ERole.GESTOR));

                usuarios.add(new User(2,"emailCliente@mail.com", enc_password, ERole.CLIENTE));

                for (User usuario : usuarios) {
                    if (usuario.getUsername().equals(email)) {
                        return usuario;
                    }
                }
                throw new UsernameNotFoundException("No hay usuario");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        logger.info("Entra authenticationManager!!!!");
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // http.authenticationProvider(authProvider()); // can be commented

        http
                .authorizeHttpRequests((requests) -> requests
                                .antMatchers("/auth/login",
                                        "/docs/**",
                                        "/users",
                                        "/h2-ui/**",
                                        "/configuration/ui",
                                        "/swagger-resources/**",
                                        "/configuration/security",
                                        "/swagger-ui.html",
                                        "/webjars/**"
                                ).permitAll() // HABILITAR ESPACIOS LIBRES
//                        .antMatchers("/**").permitAll() // BARRA LIBRE
//                        .antMatchers("/products/**").hasAuthority(ERole.USER.name())
                                .antMatchers(HttpMethod.PUT, "/accouts/{id}/retirar").hasAnyAuthority(ERole.CLIENTE.name())
                                .antMatchers(HttpMethod.PUT, "/accounts/{id}/añadir").hasAnyAuthority(ERole.CLIENTE.name())//Para acceder a productos debe ser USER
                                .antMatchers(HttpMethod.POST, "/accounts").hasAnyAuthority(ERole.GESTOR.name())
                                .antMatchers(HttpMethod.DELETE, "/accounts/{id}").hasAnyAuthority(ERole.GESTOR.name())//admin puede hacer de todo
                                .anyRequest().authenticated()
                );

        http.headers(headers ->
                headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin())
        );

        http.exceptionHandling((exception) -> exception.authenticationEntryPoint(
                (request, response, ex) -> {
                    response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            ex.getMessage()
                    );
                }
        ));

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}