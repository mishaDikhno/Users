package com.dikhno.dao;

import com.dikhno.model.User;

import java.util.List;

/**
 * Created by Dexter on 03.04.2015.
 */
public interface UserDAO {
    public void addUser(User user);
    public void updateUser(User user);
    public User getUser(int id);
    public void deleteUser(int id);
    public List<User> getUsers(int page, String name);
    public Integer getNumber(String name);
}
