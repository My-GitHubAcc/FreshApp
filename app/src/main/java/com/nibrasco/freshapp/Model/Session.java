package com.nibrasco.freshapp.Model;

public class Session {
    private static User user;
    private static Cart cart;

    public static User User() {
        return user;
    }

    public static void User(User _user) {
        user = _user;
    }

    public static Cart Cart() {
        return cart;
    }

    public static void Cart(Cart _cart) {
        cart = _cart;
    }
}
