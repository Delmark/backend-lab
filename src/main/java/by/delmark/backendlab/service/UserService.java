package by.delmark.backendlab.service;

import by.delmark.backendlab.pojo.model.User;
import by.delmark.backendlab.pojo.request.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByLogin(String login);

    void registerUser(UserRequest userRequest);
}
