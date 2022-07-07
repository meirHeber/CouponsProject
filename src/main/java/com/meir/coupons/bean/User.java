package com.meir.coupons.bean;

import com.meir.coupons.enums.UserTypes;

public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private UserTypes userType;
    private Integer companyId;

    public User() {
    }

    public User(Integer id, String firstName, String lastName, String username, String password, UserTypes userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User(String firstName, String lastName, String username, String password, UserTypes userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User(Integer id, String firstName, String lastName, String username, String password, UserTypes userType, Integer companyId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.companyId = companyId;
    }

    public User(String firstName, String lastName, String username, String password, UserTypes userType, Integer companyId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", companyId=" + companyId +
                '}';
    }

}
