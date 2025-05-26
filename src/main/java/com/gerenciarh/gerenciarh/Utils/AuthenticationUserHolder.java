package com.gerenciarh.gerenciarh.Utils;

import com.gerenciarh.gerenciarh.Models.User;

public class AuthenticationUserHolder {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void set(User user) {
        currentUser.set(user);
    }

    public static User get() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }

}
