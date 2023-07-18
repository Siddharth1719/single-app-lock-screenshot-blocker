package com.applock.singleapplock;

import com.scottyab.aescrypt.AESCrypt;

import javax.crypto.BadPaddingException;

public class DecryptStringHelper {
    public static String decryptString(String str, String str2) {
        String str3 = ApplicationSettings.PASSWORD[0];
        if (str2 == null || str2.equals("")) {
            str2 = str3;
        }
        try {
            try {
                return AESCrypt.decrypt(str2, str);
            } catch (BadPaddingException unused) {
                return "";
            } catch (Exception unused2) {
                return null;
            }
        } catch (Exception unused4) {
            return null;
        }
    }
}