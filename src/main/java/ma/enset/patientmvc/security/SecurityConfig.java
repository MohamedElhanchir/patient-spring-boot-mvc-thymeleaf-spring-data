package ma.enset.patientmvc.security;

/*import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
      public void configure(HttpSecurity http) throws Exception {
          http.formLogin(Customizer.withDefaults());
          http.authorizeRequests().anyRequest().authenticated();
    }

    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password("{noop}1234").roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder().encode("1234")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("1234")).roles("USER", "ADMIN");
    }

    @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {



    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
        String encodedPassword = passwordEncoder.encode("1234");
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(encodedPassword).roles("USER").build(),
                User.withUsername("user2").password(encodedPassword).roles("USER").build(),
                User.withUsername("admin").password(encodedPassword).roles("USER","ADMIN").build(),
                User.withUsername("admin2").password(encodedPassword).roles("ADMIN").build()
        );
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource)
    {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index", true)
                        .permitAll()
                );
        httpSecurity.authorizeRequests().requestMatchers("/webjars/**").permitAll();
        httpSecurity.authorizeRequests().requestMatchers("/index/**").hasAnyRole("USER","ADMIN");
        httpSecurity.authorizeRequests().requestMatchers("/user/**").hasRole("USER");
        httpSecurity.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        httpSecurity.authorizeRequests().requestMatchers("/").permitAll();
        httpSecurity.authorizeRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling(e -> e
                .accessDeniedPage("/notAuthorized"));
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
      return httpSecurity
                .formLogin().loginPage("/login").permitAll()
                .authorizeHttpRequests(ar -> ar
                        .requestMatchers("/webjars/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/index/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/").permitAll()
                    .anyRequest().authenticated())
                .exceptionHandling(e -> e
                    .accessDeniedPage("/notAuthorized"))
                .build();
     */


}
