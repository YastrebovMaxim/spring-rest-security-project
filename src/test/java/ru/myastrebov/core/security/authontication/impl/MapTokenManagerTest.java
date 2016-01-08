package ru.myastrebov.core.security.authontication.impl;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import ru.myastrebov.core.UserInfo;
import ru.myastrebov.core.security.authontication.TokenInfo;
import ru.myastrebov.core.security.authontication.TokenManager;
import ru.myastrebov.core.security.authontication.UserContext;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class MapTokenManagerTest {

    private TokenManager uut = new MapTokenManager();

    @Test
    public void testCreateToken() throws Exception {
        TokenInfo tokenInfo = uut.createNewToken(new UserContext(createUser()));
        assertThat(tokenInfo, is(notNullValue()));
        assertFalse(StringUtils.isEmpty(tokenInfo.getToken()));
        assertThat(tokenInfo.getUserDetails().getUsername(), equalTo("Maxim"));
        assertThat(tokenInfo.getCreateTime(), is(notNullValue()));
    }

    @Test
    public void testRemoveToken() throws Exception {
        TokenInfo tokenInfo = uut.createNewToken(new UserContext(createUser()));

        assertThat(uut.getUserDetails(tokenInfo.getToken()), is(notNullValue()));
        //repeat
        assertThat(uut.getUserDetails(tokenInfo.getToken()), is(notNullValue()));
        //first time return not null
        assertThat(uut.removeToken(tokenInfo.getToken()), is(notNullValue()));
        //we deleted already so must be null
        assertThat(uut.removeToken(tokenInfo.getToken()), is(nullValue()));
    }

    @Test
    public void testGetByToken() throws Exception {
        TokenInfo tokenInfo = uut.createNewToken(new UserContext(createUser()));
        assertThat(uut.getUserDetails(tokenInfo.getToken()), is(notNullValue()));
        //repeat
        assertThat(uut.getUserDetails(tokenInfo.getToken()), is(notNullValue()));
    }

    @Test
    public void testGetValidUsers() throws Exception {
        Map<String, UserDetails> beforeUserMap = uut.getValidUsers();
        assertThat(beforeUserMap.size(), equalTo(0));

        uut.createNewToken(new UserContext(createUser()));

        Map<String, UserDetails> afterUserMap = uut.getValidUsers();
        assertThat(beforeUserMap.size(), equalTo(0));
        assertThat(afterUserMap.size(), equalTo(1));
    }

    private UserInfo createUser() {
        return new UserInfo(1L, "Maxim", "1", Lists.newArrayList());
    }
}