package ru.myastrebov.core.security.authontication;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * @author Maxim
 */
public interface TokenManager {
    /**
     * create and store new token for user
     * @param userDetails user info
     * @return token
     */
    TokenInfo createNewToken(UserDetails userDetails);

    /**
     * Invalidate token
     * @param token token
     * @return userDetails for token
     */
    UserDetails removeToken(String token);

    /**
     * Return user information by token if it present
     * @param token token
     * @return user info
     */
    UserDetails getUserDetails(String token);

    /**
     * @return Return all valid users
     */
    Map<String, UserDetails> getValidUsers();
}
