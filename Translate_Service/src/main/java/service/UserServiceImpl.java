package service;

import mapper.UserMapper;
import pojo.User;

public class UserServiceImpl implements UserService{
    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User selectUserByAccount(String account){
        return userMapper.selectUserByAccount(account);
    }

    @Override
    public void InsertUser(User user) {
        userMapper.InsertUser(user);
        return;
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    ;
}
