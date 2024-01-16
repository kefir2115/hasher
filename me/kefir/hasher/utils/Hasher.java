package me.kefir.hasher.utils;

import java.io.File;

public class Hasher {

    public String hash(File f, String content) {
        content = f.getName()+"\n"+content;
        return hash(content);
    }

    public String hash(String str) {
        StringBuilder sb = new StringBuilder();

        for(char c : str.toCharArray()) {
            int id = c;
            int nid = id*id+10;
            char ch = (char) nid;
            sb.append(ch);
        }

        return sb.toString();
    }

    public String dehash(String str) {
        StringBuilder sb = new StringBuilder();

        for(char c : str.toCharArray()) {
            int id = c-10;
            int oid = (int) Math.sqrt(id);
            char ch = (char) oid;
            sb.append(ch);
        }

        return sb.toString();
    }
}
