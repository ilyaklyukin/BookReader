package com.umnix.bookreader.db;


public class Codec {

    public static String encode(String text, String securityKey) {
        byte[] key = securityKey.getBytes();

        StringBuilder sb = new StringBuilder();
        char temp;
        for (int i = 0; i < text.length(); i++) {
            temp = (char) (text.charAt(i) ^ key[i % key.length]);
            sb.append(temp);
        }

        return sb.toString();
    }

    public static String decode(String text, String securityKey) {
        byte[] key = securityKey.getBytes();

        char temp;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            temp = (char) (text.charAt(i) ^ key[i % key.length]);
            sb.append(temp);
        }

        return sb.toString();
    }
}
