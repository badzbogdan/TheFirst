package com.example.bogdan.thefirst;

import java.util.HashMap;
import java.util.Map;

class AccountDB {

    // <email, MD5 pass>
    private Map<String, byte[]> accounts = new HashMap<>();

    void addAccount(String email, byte[] md5Pass) {
        accounts.put(email, md5Pass);
    }

    byte[] getPass(String email) {
        return accounts.get(email);
    }

    boolean containsEmail(String email) {
        return accounts.containsKey(email);
    }

}
