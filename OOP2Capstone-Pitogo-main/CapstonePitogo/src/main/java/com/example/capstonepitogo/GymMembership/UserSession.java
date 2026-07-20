package com.example.capstonepitogo.GymMembership;

import java.io.*;

public class UserSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SESSION_FILE = "session.dat";

    private final int userId;
    private final String role;

    // Private constructor for serialization handling
    private UserSession(int userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    // Creates and writes the session to session.dat
    public static void createSession(int userId, String role) {
        UserSession session = new UserSession(userId, role);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE))) {
            oos.writeObject(session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retrieves the logged-in user's ID from session.dat
    public static int getLoggedInUserId() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_FILE))) {
            UserSession session = (UserSession) ois.readObject();
            return session.userId;
        } catch (IOException | ClassNotFoundException e) {
            return 0; // Returns 0 if no active session file exists
        }
    }

    // Retrieves the role from session.dat
    public static String getLoggedInRole() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_FILE))) {
            UserSession session = (UserSession) ois.readObject();
            return session.role;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }


    public static void clearSession() {
        File file = new File(SESSION_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}