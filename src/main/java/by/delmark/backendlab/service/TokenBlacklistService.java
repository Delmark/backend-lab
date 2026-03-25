package by.delmark.backendlab.service;

public interface TokenBlacklistService {
    void putToken(String token);
    boolean isTokenBlacklisted(String token);
}
