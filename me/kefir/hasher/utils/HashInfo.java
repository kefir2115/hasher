package me.kefir.hasher.utils;

import java.io.File;

public class HashInfo {

    public File normalFileLocation;
    public String content;

    public HashInfo(File hashedLocation, String str) {
        String[] spl = str.split("\n");
        normalFileLocation = new File(hashedLocation.getAbsolutePath().replace(hashedLocation.getName(), spl[0]));
        content = str.replace(spl[0]+"\n", "");
    }

    public File getNormalFileLocation() {
        return normalFileLocation;
    }

    public String getContent() {
        return content;
    }
}
