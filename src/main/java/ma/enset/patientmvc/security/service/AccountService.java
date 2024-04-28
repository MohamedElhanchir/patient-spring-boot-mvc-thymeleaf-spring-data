package ma.enset.patientmvc.security.service;

import ma.enset.patientmvc.security.entities.AppRole;
import ma.enset.patientmvc.security.entities.AppUser;

public interface AccountService {
    /*
    *tous les choses qu'on fait avec JdbcUserDetailsManager on peut les faire avec UserDetailsManager
    *Mais à nous de les implémenter
     */

    AppUser addNewUser(String username, String password,String email, String confirmedPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    AppUser loadUserByUsername(String username);
    void removeRoleFromUser(String username, String role);
}
