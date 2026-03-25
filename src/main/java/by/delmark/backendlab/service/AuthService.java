package by.delmark.backendlab.service;

import by.delmark.backendlab.pojo.request.TokenResponse;
import by.delmark.backendlab.pojo.request.UserRequest;

public interface AuthService {
    TokenResponse authorize(UserRequest userRequest);
    void invalidateSession();
}
