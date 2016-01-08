package ru.myastrebov.core.repositories;

import ru.myastrebov.core.UserInfo;

/**
 * Repository for {@link ru.myastrebov.core.UserInfo}
 * fixme use Spring Data
 * @author Maxim
 */
public interface UserRepository {
    /**
     * Find user by name
     * @param userName name
     * @return userInfo if exist
     */
    UserInfo findByName(String userName);
}
