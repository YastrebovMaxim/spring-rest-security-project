package ru.myastrebov.core.repositories.inMemory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import ru.myastrebov.core.UserInfo;
import ru.myastrebov.core.repositories.UserRepository;
import ru.myastrebov.core.security.UserRole;

import java.util.Map;
import java.util.Optional;

/**
 * In memory implementation.
 * @author Maxim
 */
@Component
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, UserInfo> userMap = Maps.newHashMap();

    public InMemoryUserRepository() {
        userMap.put(1L, new UserInfo(1L, "admin", "pass", Lists.newArrayList(UserRole.ADMIN)));
        userMap.put(2L, new UserInfo(2L, "Maxim", "123", Lists.newArrayList(UserRole.USER)));
    }

    @Override
    public UserInfo findByName(String userName) {
        Optional<UserInfo> userInfoOptional = userMap
                .values()
                .stream()
                .filter(userInfo -> userInfo.getUserName().equals(userName))
                .findFirst();
        return userInfoOptional.isPresent() ? userInfoOptional.get() : null;
    }
}
