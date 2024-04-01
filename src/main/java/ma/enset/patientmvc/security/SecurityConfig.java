package ma.enset.patientmvc.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
        String encodedPassword = passwordEncoder.encode("1234");
        //System.out.println(encodedPassword);
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(encodedPassword).roles("USER").build(),
                User.withUsername("user2").password(encodedPassword).roles("USER").build(),
                User.withUsername("admin").password(encodedPassword).roles("USER","ADMIN").build(),
                User.withUsername("admin2").password(encodedPassword).roles("ADMIN").build()
        );
    }

   /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(ar -> ar
                        .requestMatchers("/webjars/**").permitAll()
                    .requestMatchers("/delete/**","/formPatients/**","/edit/**" ,"/editSave/**","/save/**").hasRole("ADMIN")
                        .requestMatchers("/index/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/").permitAll()
                    .anyRequest().authenticated())
                .exceptionHandling(e -> e
                    .accessDeniedPage("/notAuthorized"))
                .build();
    }*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(ar -> ar
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e
                        .accessDeniedPage("/notAuthorized"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
