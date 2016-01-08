package ru.myastrebov.core.repositories.inMemory;

import org.junit.Test;
import ru.myastrebov.core.UserInfo;
import ru.myastrebov.core.repositories.UserRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class InMemoryUserRepositoryTest {

    private UserRepository uut = new InMemoryUserRepository();

    @Test
    public void testSuccessFindByName() throws Exception {
        String userName = "admin";

        UserInfo admin = uut.findByName(userName);
        assertThat(admin, is(notNullValue()));
        assertThat(admin.getUserName(), equalTo(userName));
    }

    @Test
    public void testFailureFindUserByName() throws Exception {
        UserInfo userInfo = uut.findByName("1235");
        assertThat(userInfo, is(nullValue()));

    }
}