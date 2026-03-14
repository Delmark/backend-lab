package by.delmark.backendlab.service.impl;

import by.delmark.backendlab.dao.UserDAO;
import by.delmark.backendlab.mapstruct.UserMapper;
import by.delmark.backendlab.pojo.model.User;
import by.delmark.backendlab.pojo.request.UserRequest;
import by.delmark.backendlab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByLogin(String login) {
        return userDAO.getByLogin(login);
    }

    @Override
    public void registerUser(UserRequest userRequest) {
        userDAO.getByLogin(userRequest.getLogin()).ifPresent(_ -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь с таким логином уже существует");
        });

        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userDAO.saveUser(UserMapper.INSTANCE.toModel(userRequest));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
