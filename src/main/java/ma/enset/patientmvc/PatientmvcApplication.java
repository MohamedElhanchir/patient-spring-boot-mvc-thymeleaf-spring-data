package ma.enset.patientmvc;

import ma.enset.patientmvc.entities.Patient;
import ma.enset.patientmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class PatientmvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientmvcApplication.class, args);
    }

    @Bean
    CommandLineRunner start(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null, "Hassan", new Date(), false, 400));
            patientRepository.save(new Patient(null, "Khalid", new Date(), false, 500));
            patientRepository.save(new Patient(null, "Omar", new Date(), true, 600));
            patientRepository.save(new Patient(null, "Salma", new Date(), false, 700));
            patientRepository.save(new Patient(null, "Hanae", new Date(), true, 800));
        };
    }

    @Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return args -> {
            if(!jdbcUserDetailsManager.userExists("admin3")) {
                jdbcUserDetailsManager.createUser(User.withUsername("admin3").password(passwordEncoder.encode("1234")).roles("ADMIN").build());
            }
            if(!jdbcUserDetailsManager.userExists("user3")) {
                jdbcUserDetailsManager.createUser(User.withUsername("user3").password(passwordEncoder.encode("1234")).roles("USER").build());
            }
            if(!jdbcUserDetailsManager.userExists("admin4")) {
                jdbcUserDetailsManager.createUser(User.withUsername("admin4").password(passwordEncoder.encode("1234")).roles("ADMIN","USER").build());
            }
        };

    }




}
