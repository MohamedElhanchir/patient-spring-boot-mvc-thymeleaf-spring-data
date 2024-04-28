package ma.enset.patientmvc.security.repo;

import ma.enset.patientmvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
      AppRole findByRole(String role);
 }
