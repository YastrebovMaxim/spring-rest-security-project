package ru.myastrebov.core.security.authontication.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.myastrebov.core.security.authontication.AuthenticationService;
import ru.myastrebov.core.security.authontication.TokenInfo;
import ru.myastrebov.core.security.authontication.TokenManager;

/**
 * @author Maxim
 */
@Service
public class DefaultAuthenticationService implements AuthenticationService {

    private TokenManager tokenManager;
    private AuthenticationManager authenticationManager;

    @Autowired
    public DefaultAuthenticationService(TokenManager tokenManager, AuthenticationManager authenticationManager) {
        this.tokenManager = tokenManager;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenInfo authenticate(String userName, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userName, password);
        try {
            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (authentication.getPrincipal() != null) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                TokenInfo newToken = tokenManager.createNewToken(userDetails);
                if (newToken == null) {
                    return null;
                }
                return newToken;
            }
        } catch (AuthenticationException e) {
            System.out.println("authenticate - FAILED: " + e.toString());
        }
        return null;
    }

    @Override
    public boolean checkToken(String token) {
        return tokenManager.getUserDetails(token) != null;
    }

    @Override
    public void logout(String token) {
        UserDetails logoutUser = tokenManager.removeToken(token);
        System.out.println("logout: " + logoutUser.getUsername());
        SecurityContextHolder.clearContext();
    }
}
