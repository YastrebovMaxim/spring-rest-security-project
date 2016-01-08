package ru.myastrebov.core.security.authontication.impl;

import com.google.common.collect.Maps;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.myastrebov.core.security.authontication.TokenInfo;
import ru.myastrebov.core.security.authontication.TokenManager;

import java.util.Map;
import java.util.UUID;

/**
 * Token manager based on map
 * @author Maxim
 */
@Component
public class MapTokenManager implements TokenManager {

    private final Map<String, UserDetails> tokenMap = Maps.newConcurrentMap();

    @Override
    public TokenInfo createNewToken(UserDetails userDetails) {
        String token = generateToken();
        tokenMap.put(token, userDetails);
        return new TokenInfo(token, userDetails);
    }

    @Override
    public UserDetails removeToken(String token) {
        return tokenMap.remove(token);
    }

    @Override
    public UserDetails getUserDetails(String token) {
        return tokenMap.get(token);
    }

    @Override
    public Map<String, UserDetails> getValidUsers() {
        return Maps.newHashMap(tokenMap);
    }

    /**
     * todo make interface and inject
     * @return random string
     */
    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
