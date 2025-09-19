package com.autotech.autotech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.autotech.autotech.auth.Usuario;
import com.autotech.autotech.auth.UsuarioRepository;

@Configuration
public class SecurityConfig {

  @Bean
  UserDetailsService userDetailsService(UsuarioRepository repo) {
    return (String correo) -> {
      Usuario u = repo.findByCorreo(correo)
          .orElseThrow(() -> new UsernameNotFoundException("No existe: " + correo));
      // Roles mínimos; ajusta si tienes tabla de roles
      return User.withUsername(u.getCorreo())
          .password(u.getPassword())  // por ahora texto plano
          .roles("USER")
          .build();
    };
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    // SOLO para pruebas con password en texto plano
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  SecurityFilterChain filter(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/css/**","/images/**","/js/**","/login").permitAll()
        .anyRequest().authenticated()
      )
      .formLogin(login -> login
        .loginPage("/login")
        .usernameParameter("correo")     // <-- coincide con tu input name
        .passwordParameter("password")   // <-- coincide con tu input name
        .defaultSuccessUrl("/dashboard", true)

        .permitAll()
      )
      .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());
    return http.build();
  }
}
