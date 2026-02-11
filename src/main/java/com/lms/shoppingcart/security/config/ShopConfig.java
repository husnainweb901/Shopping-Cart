package com.lms.shoppingcart.security.config;

import com.lms.shoppingcart.security.jwt.AuthTokenFilter;
import com.lms.shoppingcart.security.jwt.JwtAuthEntryPoint;
import com.lms.shoppingcart.security.jwt.JwtUtils;
import com.lms.shoppingcart.security.user.ShopUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ShopConfig {

    private static final List<String> SECURED_URLS =
            List.of("/api/carts/**","/api/cart-items/**");
    private final ShopUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint authEntryPoint;
    private final AuthTokenFilter authTokenFilter;


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig){
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
                        .anyRequest().permitAll());
        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
