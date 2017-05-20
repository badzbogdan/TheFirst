package com.example.bogdan.thefirst;

import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;

import java.security.*;
import java.util.Arrays;

class AccountManager {

    static final int MIN_EMAIL_LENGTH = 6;
    static final int MIN_PASS_LENGTH = 4;

    private static class SingletonHolder {
        static AccountManager instance = new AccountManager();
    }

    static AccountManager getInstance() {
        return SingletonHolder.instance;
    }

    private AccountDB accountDB = new AccountDB();

    private String currentEmail;

    private AccountManager() {}

    String getCurrentEmail() {
        return currentEmail;
    }

    void clearCurrentEmail() {
        currentEmail = null;
    }

    boolean logIn(String email, String pass) {
        if (!emailAndPasswordIsValid(email, pass)) {
            return false;
        }

        if (!accountDB.containsEmail(email)) {
            return false;
        }

        byte[] md5Pass = convertToMD5(pass);
        if (md5Pass == null) {
            return false;
        }

        byte[] md5PassFromDB = accountDB.getPass(email);
        if (!Arrays.equals(md5Pass, md5PassFromDB)) {
            return false;
        }

        currentEmail = email;
        return true;
    }

    ResultType addAccount(String email, String pass, String confirmationPass) {
        if (!emailAndPasswordIsValid(email, pass)) {
            return ResultType.INVALID_EMAIL_OR_PASSWORD;
        }

        if (!pass.equals(confirmationPass)) {
            return ResultType.PASSWORDS_DO_NOT_MATCH;
        }

        if (accountDB.containsEmail(email)) {
            return ResultType.LOGIN_IS_EXIST;
        }

        byte[] md5Pass = convertToMD5(pass);
        if (md5Pass == null) {
            return ResultType.MD5_CONVERT_EXCEPTION;
        }

        accountDB.addAccount(email, md5Pass);
        return ResultType.SUCCESS;
    }

    private boolean emailAndPasswordIsValid(String email, String pass) {
        return !(email == null || pass == null || email.length() < MIN_EMAIL_LENGTH
                || pass.length() < MIN_PASS_LENGTH);
    }

    @Nullable
    private byte[] convertToMD5(String pass) {
        byte[] bytesOfPass;
        try {
            bytesOfPass = pass.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        return md.digest(bytesOfPass);
    }

}
