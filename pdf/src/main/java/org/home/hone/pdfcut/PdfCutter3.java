package org.home.hone.pdfcut;

import org.ghost4j.util.StreamGobbler;

import java.io.File;

public class PdfCutter3 {
    private static final String GHOSTSCRIPT_CMD = "gs";

    public static int split(String pdfFile) {

        ProcessBuilder processBuilder = new ProcessBuilder(
            GHOSTSCRIPT_CMD,
            "-dQUIET",
            "-dNOPAUSE",
            "-sDEVICE=jpeg",
            "-o %03d.jpg",
            "-dJPEGQ=100",
            "-r144",
            pdfFile
        );
        processBuilder.directory(new File("/Users/justin/Downloads"));
        processBuilder.environment().putAll(System.getenv());

        // start
        try {
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // redirect output stream to main process output stream
            // error stream
            // standard stream
            StreamGobbler outputStreamGobbler = new StreamGobbler(process.getInputStream(), System.out);
            outputStreamGobbler.start();
            process.waitFor();
            // harvest the image and store into TFS
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;

    }

    public static void main(String[] args) {
        String pdfFile = "/Users/justin/Downloads/filled.pdf";
        split(pdfFile);
    }

}
