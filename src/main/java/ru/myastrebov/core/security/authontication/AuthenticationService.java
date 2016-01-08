package ru.myastrebov.core.security.authontication;

/**
 * @author Maxim
 */
public interface AuthenticationService {

    /**
     * authentication process
     * @param userName user name
     * @param password password
     * @return token information
     */
    TokenInfo authenticate(String userName, String password);

    /**
     * Is token valid?
     * @param token token
     * @return true if we have user with this token and this token is valid
     */
    boolean checkToken(String token);

    /**
     * logout
     * @param token token for this user
     */
    void logout(String token);
}
