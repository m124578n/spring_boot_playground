package com.example.todolist.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hash {

    private static String salt = "SpringBoot";

    public static String generateHash(String account, String password) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String hashStr = account + salt + password;
            byte[] hashBytes = md.digest(hashStr.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } finally {

        }
    }
}
