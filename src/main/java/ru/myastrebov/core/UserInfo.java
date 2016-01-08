package ru.myastrebov.core;

import com.google.common.collect.Lists;
import ru.myastrebov.core.security.UserRole;

import java.util.List;

/**
 * User information holder
 * @author Maxim
 */
public class UserInfo {
    private Long id;
    private String userName;
    private String password;
    private List<UserRole> userRoleList = Lists.newArrayList();

    public UserInfo(Long id, String userName, String password, List<UserRole> userRoleList) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.setUserRoleList(userRoleList);
    }

    public UserInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        if (userRoleList != null) {
            this.userRoleList = userRoleList;
        } else {
            this.userRoleList = Lists.newArrayList();
        }
    }
}
