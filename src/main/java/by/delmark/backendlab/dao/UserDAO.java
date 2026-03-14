package by.delmark.backendlab.dao;

import by.delmark.backendlab.pojo.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserDAO {
    Optional<User> getByLogin(String login);

    void saveUser(User user);
}
