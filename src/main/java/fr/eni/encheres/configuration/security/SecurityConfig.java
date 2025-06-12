package fr.eni.encheres.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource){

//        System.out.println("SecurityConfig.userDetailsManager");
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, mot_de_passe, 1 FROM utilisateurs WHERE email=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT email, role FROM utilisateurs u INNER JOIN roles r ON r.is_admin = u.administrateur WHERE u.email=?");
       // System.out.println("jdbcUserDetailsManager :"+jdbcUserDetailsManager);
        return jdbcUserDetailsManager;
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/index").permitAll()
                            .requestMatchers("/login").permitAll()
                            .requestMatchers("/utilisateur/inscription").permitAll()
                            .requestMatchers("/css/**").permitAll()
                            .requestMatchers("/images/**").permitAll()
                            .anyRequest().authenticated();
                });

        //http.formLogin(Customizer.withDefaults());

        http.formLogin( form -> {
            form.loginPage("/login").permitAll()
                    .defaultSuccessUrl("/", true);

        });

        http.logout(logout -> {
            logout.invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/");
        });

        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        );

        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
