package com.example.capstonepitogo.GymMembership;

public class UserSession {
    private static int loggedInUserId;

    public static void setLoggedInUserId(int userId) {
        loggedInUserId = userId;
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }
}