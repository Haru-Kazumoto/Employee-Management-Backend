package project.pack.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import project.pack.User.repository.UserRepository;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public UserDetailsService userDetailsService(){
        return (username) -> repository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Username %s not found!",username)
                ));
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http
                .cors().and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/auth/register",
                            "/api/v1/admin/**"
                    ).permitAll();
                    try {
                        auth.anyRequest()
                                .fullyAuthenticated()
                                .and()
                                .formLogin()
                                .and()
                                .logout()
                                .logoutSuccessUrl("/auth/logout");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }
}
