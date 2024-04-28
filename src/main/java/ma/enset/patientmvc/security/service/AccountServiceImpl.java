package ma.enset.patientmvc.security.service;

import lombok.AllArgsConstructor;
import ma.enset.patientmvc.security.entities.AppRole;
import ma.enset.patientmvc.security.entities.AppUser;
import ma.enset.patientmvc.security.repo.AppRoleRepository;
import ma.enset.patientmvc.security.repo.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmedPassword) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user != null) throw new RuntimeException("User already exists");
        if (!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
        user = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder().encode(password))
                .email(email)
                .build();
        return appUserRepository.save(user);
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole != null) throw new RuntimeException("Role already exists");
        appRole = AppRole.builder().role(role).build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("User not found");
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole == null) throw new RuntimeException("Role not found");
        user.getRoles().add(appRole);
        /*
        * On peut faire ça aussi mais c'est pas recommandé car on va faire un update sur tous les roles
        * appRole.getUsers().add(user);
        * aussi la classe est transactionnelle donc on peut faire ça directement
         */
        //appUserRepository.save(user);

    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("User not found");
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole == null) throw new RuntimeException("Role not found");
        user.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
