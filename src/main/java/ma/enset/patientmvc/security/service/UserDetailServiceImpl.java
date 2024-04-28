    package ma.enset.patientmvc.security.service;

    import lombok.AllArgsConstructor;
    import ma.enset.patientmvc.security.entities.AppUser;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.stereotype.Service;

    @Service
    @AllArgsConstructor
    public class UserDetailServiceImpl implements UserDetailsService {
        private AccountService accountService;


        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            AppUser user = accountService.loadUserByUsername(username);
            if(user==null) throw new UsernameNotFoundException("Invalid user");

            UserDetails userDetails = User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().stream().map(r->r.getRole()).toArray(String[]::new))
                    .build();
            return userDetails;
        }
    }
