package org.home.hone.pdfcut;

/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;

public class FillForm {

    public static final String SRC = "/Users/justin/Downloads/CertificateOfExcellence.pdf";
    public static final String DEST = "certificate.pdf";

    public static void main(String[] args) throws DocumentException, IOException {
        new FillForm().manipulatePdf(SRC, DEST);
    }

    public void manipulatePdf(String src, String dest) throws DocumentException, IOException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        AcroFields form = stamper.getAcroFields();
        form.setField("course", "艺术家");
        form.setField("name", "Some dude on StackOverflow");
        form.setField("date", "April 10, 2016");
        form.setField("description",
            "In this course, people consistently ignore the existing documentation completely. "
                + "They are encouraged to do no effort whatsoever, but instead post their questions "
                + "on StackOverflow. It would be a mistake to refer to people completing this course "
                + "as developers. A better designation for them would be copy/paste artist. "
                + "Only in very rare cases do these people know what they are actually doing. "
                + "Not a single student has ever learned anything substantial during this course.");
        stamper.setFormFlattening(true);
        stamper.close();
    }
}
