package com.blogproject.blog.config;



import com.blogproject.blog.domain.entities.User;
import com.blogproject.blog.repositories.UserRepository;
import com.blogproject.blog.security.BlogUserDetailsService;
import com.blogproject.blog.security.JwtAuthenticationFilter;
import com.blogproject.blog.services.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService){
        return new JwtAuthenticationFilter(authenticationService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        BlogUserDetailsService blogUserDetailsService= new BlogUserDetailsService(userRepository);
        String email="example@123";
        userRepository.findByEmail(email).orElseGet(()->{
            User newUser=User
                    .builder()
                    .name("testUser")
                    .email(email)
                    .password(passwordEncoder().encode("password"))
                    .build();
            return userRepository.save(newUser);
        });
        return blogUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.
                authorizeHttpRequests(auth ->auth
                        .requestMatchers(HttpMethod.POST,"api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/v1/posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/v1/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/v1/tags/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    //watchman/gatekeeper for verifying username and password
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
