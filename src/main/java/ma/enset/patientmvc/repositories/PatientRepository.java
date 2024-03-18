package ma.enset.patientmvc.repositories;

import ma.enset.patientmvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByMalady(boolean malady);
    Page<Patient> findByMalady(boolean malady, Pageable pageable);
    Page<Patient> findByNameContains(String kw, Pageable pageable);
    List<Patient> findByNameContains(String name);
    List<Patient> findByNameContainsAndMalady(String name, boolean malady);
    List<Patient> findByMaladyIsTrueAndScore(int score);
    List<Patient> findByDateOfBirthBetween(Date date1, Date date2);
    @Query("select p from Patient p where p.name like :x and p.dateOfBirth > :y and p.dateOfBirth < :z")
    List<Patient> chercher(@Param("x") String name, @Param("y") Date date1, @Param("z") Date date2);


}
