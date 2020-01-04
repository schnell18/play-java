package org.home.hone.pdfcut;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PdfFiller2 {

    public static int fillForm(String file, Map<String, String> formData) {
        try {
            PdfReader reader = new PdfReader(file);
            String dest = "filled-ghost4j.pdf";
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
            AcroFields form = stamper.getAcroFields();
            form.setGenerateAppearances(true);
            BaseFont unicode =
                BaseFont.createFont("/Users/justin/Downloads/NotoSansCJKsc-hinted/NotoSansCJKsc-Light.otf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //BaseFont.createFont("/Users/justin/Downloads/MingLiU.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            form.addSubstitutionFont(unicode);
            for (Map.Entry<String, String> kv: formData.entrySet()) {
                form.setField(kv.getKey(), kv.getValue());
            }
            stamper.setFormFlattening(true);
            stamper.close();

            return 1;
        }
        catch (DocumentException de) {
            de.printStackTrace();
        }
        catch (IOException ie) {
            ie.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("java.awt.headless", "true");
        String pdfFile = "/Users/justin/Downloads/360_voucher_simsun.pdf";
        Map<String, String> formData = new HashMap<>();
        formData.put("billNo", "00040000009293");
        formData.put("insuranceBillNo", "P190000014382293");
        formData.put("applicant", "李时珍");
        formData.put("recognizee", "华佗");
        formData.put("effectiveYear", "2017");
        formData.put("effectiveMonth", "6");
        formData.put("effectiveDay", "1");
        fillForm(pdfFile, formData);
    }
}
