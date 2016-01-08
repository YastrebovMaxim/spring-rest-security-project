package ru.myastrebov.core.security.authontication;

import com.google.common.collect.Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.myastrebov.core.UserInfo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Maxim
 */
public class UserContext implements UserDetails {
    private final UserInfo userInfo;

    public UserContext(UserInfo userInfo) {
        if (userInfo == null) {
            throw new NullPointerException();
        }
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = Lists.newArrayList();
        authorities.addAll(userInfo
                .getUserRoleList()
                .stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getName()))
                .collect(Collectors.toList()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
