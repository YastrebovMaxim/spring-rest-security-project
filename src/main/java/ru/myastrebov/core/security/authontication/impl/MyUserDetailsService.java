package ru.myastrebov.core.security.authontication.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.myastrebov.core.UserInfo;
import ru.myastrebov.core.repositories.UserRepository;
import ru.myastrebov.core.security.authontication.UserContext;

/**
 * @author Maxim
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByName(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User with name = " + username + " not found");
        }
        return new UserContext(userInfo);
    }
}
