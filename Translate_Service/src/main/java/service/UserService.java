package service;

import pojo.User;

public interface UserService {

    public User selectUserByAccount(String account);

    public void InsertUser(User user);

    public void updateUser(User user);
}
