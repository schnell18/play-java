package org.home.hone.io;

import java.io.File;

public class TempFile {
    public static void main(String[] args) {
        File dir = new File(System.getProperty("java.io.tmpdir"), "poifiles");
        System.out.println("target dir: " + dir.getPath());
        boolean r = dir.mkdir();
        System.out.println("first call of mkdir() returns: " + r);
        r = dir.mkdir();
        System.out.println("second call of mkdir() returns: " + r);
    }
}
