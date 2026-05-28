package com.condominio.common.security;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration @EnableWebSecurity @EnableMethodSecurity @RequiredArgsConstructor
public class SecurityConfig {
    @Bean public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception { return cfg.getAuthenticationManager(); }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(a -> a
                .requestMatchers("/login","/acesso-negado","/css/**","/js/**","/images/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .formLogin(f -> f.loginPage("/login").defaultSuccessUrl("/dashboard",true).failureUrl("/login?error").permitAll())
            .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
            .exceptionHandling(e -> e.accessDeniedPage("/acesso-negado"))
            .headers(h -> h.frameOptions(fo -> fo.sameOrigin()))
            .csrf(c -> c.ignoringRequestMatchers("/h2-console/**"));
        return http.build();
    }
}
