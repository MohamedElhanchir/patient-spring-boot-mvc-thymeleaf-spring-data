package ma.enset.patientmvc;

import ma.enset.patientmvc.entities.Patient;
import ma.enset.patientmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientmvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientmvcApplication.class, args);
    }

    @Bean
    CommandLineRunner start(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null, "Hassan", new Date(), false, 4));
            patientRepository.save(new Patient(null, "Khalid", new Date(), false, 5));
            patientRepository.save(new Patient(null, "Omar", new Date(), true, 6));
            patientRepository.save(new Patient(null, "Salma", new Date(), false, 7));
            patientRepository.save(new Patient(null, "Hanae", new Date(), true, 8));
        };
    }

}
