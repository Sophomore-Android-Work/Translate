package mapper;

import pojo.User;

public interface UserMapper {

        public User selectUserByAccount(String account);

        public void InsertUser(User user);

        public void updateUser(User user);
}
