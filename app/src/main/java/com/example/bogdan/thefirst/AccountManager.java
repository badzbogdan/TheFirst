package com.example.bogdan.thefirst;

import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;

import java.security.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AccountManager {

    private static class SingletonHolder {
        static AccountManager instance = new AccountManager();
    }

    static AccountManager getInstance() {
        return SingletonHolder.instance;
    }

    private AccountDB accountDB;

    private String currentEmail;

    private AccountManager() {}

    void openDB(DBHelper dbHelper) {
        accountDB = new AccountDB(dbHelper);
    }

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

        String md5Pass = convertToMD5(pass);
        if (md5Pass == null) {
            return false;
        }

        String md5PassFromDB = accountDB.getPass(email);
        if (!md5Pass.equals(md5PassFromDB)) {
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

        String md5Pass = convertToMD5(pass);
        if (md5Pass == null) {
            return ResultType.MD5_CONVERT_EXCEPTION;
        }

        accountDB.addAccount(email, md5Pass);
        return ResultType.SUCCESS;
    }

    private boolean emailAndPasswordIsValid(String email, String pass) {
        int MIN_EMAIL_LENGTH = 6;
        int MIN_PASS_LENGTH = 4;
        return !(email == null || pass == null || email.length() < MIN_EMAIL_LENGTH
                || pass.length() < MIN_PASS_LENGTH || !emailIsValid(email));
    }

    private boolean emailIsValid(String email) {
        String rgx = "^[a-zA-Z0-9._-]+@"
                + "((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])"
                + "|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(rgx);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Nullable
    private String convertToMD5(String pass) {
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

        bytesOfPass = md.digest(bytesOfPass);
        StringBuilder out = new StringBuilder(bytesOfPass.length * 2);
        for (byte b : bytesOfPass) {
            out.append(String.format("%02X", (byte) b));
        }

        return out.toString();
    }

}
