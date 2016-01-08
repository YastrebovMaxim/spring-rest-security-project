package ru.myastrebov.core.security.authontication;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * Token info holder
 * @author Maxim
 */
public class TokenInfo {
    private final Long createTime = new Date().getTime();
    private final String token;
    private final UserDetails userDetails;

    public TokenInfo(String token, UserDetails userDetails) {
        this.token = token;
        this.userDetails = userDetails;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public String getToken() {
        return token;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
