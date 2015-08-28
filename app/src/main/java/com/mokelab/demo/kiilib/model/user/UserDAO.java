package com.mokelab.demo.kiilib.model.user;

/**
 * DAO for User
 */
public interface UserDAO {
    void saveToken(String token);

    String loadToken();
}
